package com.steel.li_blog_xo.vo;

import com.steel.li_blog_base.validator.annotion.IntegerNotNull;
import com.steel.li_blog_base.validator.annotion.NotBlank;
import com.steel.li_blog_base.validator.group.GetList;
import com.steel.li_blog_base.validator.group.GetOne;
import com.steel.li_blog_base.validator.group.Insert;
import com.steel.li_blog_base.vo.BaseVO;
import lombok.Data;

/**
 * CommentVO
 *
 * @author: steel
 * @create: 2020年1月11日16:15:52
 */
@Data
public class CommentVO extends BaseVO<CommentVO> {

    /**
     * 用户uid
     */
    @NotBlank(groups = {Insert.class, GetOne.class})
    private String userUid;

    /**
     * 回复某条评论的uid
     */
    private String toUid;

    /**
     * 回复某个人的uid
     */
    private String toUserUid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 评论类型： 0: 评论   1: 点赞
     */
    private Integer type;

    /**
     * 评论内容
     */
    @NotBlank(groups = {Insert.class})
    private String content;

    /**
     * 博客uid
     */
    private String blogUid;

    /**
     * 评论来源： MESSAGE_BOARD，ABOUT，BLOG_INFO 等
     */
    @NotBlank(groups = {Insert.class, GetList.class})
    private String source;
}
