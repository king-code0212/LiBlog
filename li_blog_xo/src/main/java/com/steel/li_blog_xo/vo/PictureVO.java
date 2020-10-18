package com.steel.li_blog_xo.vo;


import com.steel.li_blog_base.validator.group.GetList;
import com.steel.li_blog_base.validator.group.Insert;
import com.steel.li_blog_base.validator.group.Update;
import com.steel.li_blog_base.vo.BaseVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 图片实体类
 *
 * @author steel
 * @date 2018年9月17日16:08:58
 */
@Data
public class PictureVO extends BaseVO<PictureVO> {

    /**
     * 图片UID
     */
    private String fileUid;

    /**
     * 图片UIDs
     */
    @NotBlank(groups = {Insert.class})
    private String fileUids;

    /**
     * 图片名称
     */
    private String picName;

    /**
     * 所属相册分类UID
     */
    @NotBlank(groups = {Insert.class, Update.class, GetList.class})
    private String pictureSortUid;
}
