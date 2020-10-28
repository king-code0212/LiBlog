package com.steel.li_blog_xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.steel.li_blog_base.enums.ECommentSource;
import com.steel.li_blog_base.enums.EStatus;
import com.steel.li_blog_base.global.BaseSQLConf;
import com.steel.li_blog_base.serviceImpl.SuperServiceImpl;
import com.steel.li_blog_common.entity.Blog;
import com.steel.li_blog_common.entity.Comment;
import com.steel.li_blog_common.entity.User;
import com.steel.li_blog_common.feign.PictureFeignClient;
import com.steel.li_blog_utils.ResultUtil;
import com.steel.li_blog_utils.StringUtils;
import com.steel.li_blog_xo.global.MessageConf;
import com.steel.li_blog_xo.global.SQLConf;
import com.steel.li_blog_xo.global.SysConf;
import com.steel.li_blog_xo.mapper.CommentMapper;
import com.steel.li_blog_xo.service.BlogService;
import com.steel.li_blog_xo.service.CommentService;
import com.steel.li_blog_xo.service.UserService;
import com.steel.li_blog_xo.utils.WebUtil;
import com.steel.li_blog_xo.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-08
 */
@Service
public class CommentServiceImpl extends SuperServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    CommentMapper commentMapper;

    @Autowired
    WebUtil webUtil;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    @Autowired
    private PictureFeignClient pictureFeignClient;

    @Override
    public Integer getCommentCount(int status) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseSQLConf.STATUS, status);
        return commentMapper.selectCount(queryWrapper);
    }

    @Override
    public IPage<Comment> getPageList(CommentVO commentVO) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(commentVO.getKeyword()) && !StringUtils.isEmpty(commentVO.getKeyword().trim())) {
            queryWrapper.like(SQLConf.CONTENT, commentVO.getKeyword().trim());
        }

        if (commentVO.getType() != null) {
            queryWrapper.eq(SQLConf.TYPE, commentVO.getType());
        }

        if (StringUtils.isNotEmpty(commentVO.getSource()) && !SysConf.ALL.equals(commentVO.getSource())) {
            queryWrapper.eq(SQLConf.SOURCE, commentVO.getSource());
        }

        if (StringUtils.isNotEmpty(commentVO.getUserName())) {
            String userName = commentVO.getUserName();
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.like(SQLConf.NICK_NAME, userName);
            userQueryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
            List<User> list = userService.list(userQueryWrapper);
            if (list.size() > 0) {
                List<String> userUid = new ArrayList<>();
                list.forEach(item -> {
                    userUid.add(item.getUid());
                });
                queryWrapper.in(SQLConf.USER_UID, userUid);
            } else {
                // 当没有查询到用户时，默认UID
                queryWrapper.in(SQLConf.USER_UID, SysConf.DEFAULT_UID);
            }
        }

        Page<Comment> page = new Page<>();
        page.setCurrent(commentVO.getCurrentPage());
        page.setSize(commentVO.getPageSize());
        queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
        queryWrapper.orderByDesc(SQLConf.CREATE_TIME);
        IPage<Comment> pageList = commentService.page(page, queryWrapper);
        List<Comment> commentList = pageList.getRecords();
        Set<String> userUidSet = new HashSet<>();
        Set<String> blogUidSet = new HashSet<>();
        commentList.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getUserUid())) {
                userUidSet.add(item.getUserUid());
            }
            if (StringUtils.isNotEmpty(item.getToUserUid())) {
                userUidSet.add(item.getToUserUid());
            }
            if (StringUtils.isNotEmpty(item.getBlogUid())) {
                blogUidSet.add(item.getBlogUid());
            }
        });

        // 获取博客
        Collection<Blog> blogList = new ArrayList<>();
        if (blogUidSet.size() > 0) {
            blogList = blogService.listByIds(blogUidSet);
        }
        Map<String, Blog> blogMap = new HashMap<>();
        blogList.forEach(item -> {
            // 评论管理并不需要查看博客内容，因此将其排除
            item.setContent("");
            blogMap.put(item.getUid(), item);
        });

        // 获取头像
        Collection<User> userCollection = new ArrayList<>();
        if (userUidSet.size() > 0) {
            userCollection = userService.listByIds(userUidSet);
        }

        final StringBuffer fileUids = new StringBuffer();
        userCollection.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getAvatar())) {
                fileUids.append(item.getAvatar() + SysConf.FILE_SEGMENTATION);
            }
        });
        String pictureList = null;
        if (fileUids != null) {
            pictureList = this.pictureFeignClient.getPicture(fileUids.toString(), SysConf.FILE_SEGMENTATION);
        }
        List<Map<String, Object>> picList = webUtil.getPictureMap(pictureList);
        Map<String, String> pictureMap = new HashMap<>();
        picList.forEach(item -> {
            pictureMap.put(item.get(SQLConf.UID).toString(), item.get(SQLConf.URL).toString());
        });
        Map<String, User> userMap = new HashMap<>();
        userCollection.forEach(item -> {
            // 判断头像是否为空
            if (pictureMap.get(item.getAvatar()) != null) {
                item.setPhotoUrl(pictureMap.get(item.getAvatar()));
            }
            userMap.put(item.getUid(), item);
        });

//        commentList.forEach(item -> {
//            ECommentSource commentSource = ECommentSource.valueOf(item.getSource());
//            item.setSourceName(commentSource.getName());
//            if (StringUtils.isNotEmpty(item.getUserUid())) {
//                item.setUser(userMap.get(item.getUserUid()));
//            }
//            if (StringUtils.isNotEmpty(item.getToUserUid())) {
//                item.setToUser(userMap.get(item.getToUserUid()));
//            }
//            if (StringUtils.isNotEmpty(item.getBlogUid())) {
//                item.setBlog(blogMap.get(item.getBlogUid()));
//            }
//        });

        for (Comment item : commentList) {
            ECommentSource commentSource = ECommentSource.valueOf(item.getSource());
            item.setSourceName(commentSource.getName());
            if (StringUtils.isNotEmpty(item.getUserUid())) {
                item.setUser(userMap.get(item.getUserUid()));
            }
            if (StringUtils.isNotEmpty(item.getToUserUid())) {
                item.setToUser(userMap.get(item.getToUserUid()));
            }
            if (StringUtils.isNotEmpty(item.getBlogUid())) {
                item.setBlog(blogMap.get(item.getBlogUid()));
            }
        }

        pageList.setRecords(commentList);
        return pageList;
    }

    @Override
    public String addComment(CommentVO commentVO) {
        Comment comment = new Comment();
        comment.setSource(commentVO.getSource());
        comment.setBlogUid(commentVO.getBlogUid());
        comment.setContent(commentVO.getContent());
        comment.setUserUid(commentVO.getUserUid());
        comment.setToUid(commentVO.getToUid());
        comment.setToUserUid(commentVO.getToUserUid());
        comment.setStatus(EStatus.ENABLE);
        comment.setUpdateTime(new Date());
        comment.insert();
        return ResultUtil.result(SysConf.SUCCESS, MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editComment(CommentVO commentVO) {
        Comment comment = commentService.getById(commentVO.getUid());
        comment.setSource(commentVO.getSource());
        comment.setBlogUid(commentVO.getBlogUid());
        comment.setContent(commentVO.getContent());
        comment.setUserUid(commentVO.getUserUid());
        comment.setToUid(commentVO.getToUid());
        comment.setToUserUid(commentVO.getToUserUid());
        comment.setStatus(EStatus.ENABLE);
        comment.setUpdateTime(new Date());
        comment.updateById();
        return ResultUtil.result(SysConf.SUCCESS, MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteComment(CommentVO commentVO) {
        Comment comment = commentService.getById(commentVO.getUid());
        comment.setStatus(EStatus.DISABLED);
        comment.setUpdateTime(new Date());
        comment.updateById();
        return ResultUtil.result(SysConf.SUCCESS, MessageConf.DELETE_SUCCESS);
    }

    @Override
    public String deleteBatchComment(List<CommentVO> commentVOList) {
        if (commentVOList.size() <= 0) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.PARAM_INCORRECT);
        }
        List<String> uids = new ArrayList<>();
        commentVOList.forEach(item -> {
            uids.add(item.getUid());
        });
        Collection<Comment> commentList = commentService.listByIds(uids);

        commentList.forEach(item -> {
            item.setUpdateTime(new Date());
            item.setStatus(EStatus.DISABLED);
        });

        commentService.updateBatchById(commentList);

        return ResultUtil.result(SysConf.SUCCESS, MessageConf.DELETE_SUCCESS);
    }

}
