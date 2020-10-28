package com.steel.li_blog_xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steel.li_blog_base.service.SuperService;
import com.steel.li_blog_common.entity.Picture;
import com.steel.li_blog_xo.vo.PictureVO;


import java.util.List;

/**
 * <p>
 * 图片表 服务类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018年9月17日16:17:22
 */
public interface PictureService extends SuperService<Picture> {

    /**
     * 获取图片列表
     *
     * @param pictureVO
     * @return
     */
    public IPage<Picture> getPageList(PictureVO pictureVO);

    /**
     * 新增图片
     *
     * @param pictureVOList
     */
    public String addPicture(List<PictureVO> pictureVOList);

    /**
     * 编辑图片
     *
     * @param pictureVO
     */
    public String editPicture(PictureVO pictureVO);

    /**
     * 批量删除图片
     *
     * @param pictureVO
     */
    public String deleteBatchPicture(PictureVO pictureVO);

    /**
     * 设置图片封面
     *
     * @param pictureVO
     */
    public String setPictureCover(PictureVO pictureVO);

    /**
     * 获取最新图片,按时间排序
     *
     * @return
     */
    public Picture getTopOne();
}
