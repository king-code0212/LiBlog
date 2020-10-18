package com.steel.li_blog_xo.vo;

import com.steel.li_blog_base.validator.annotion.IntegerNotNull;
import com.steel.li_blog_base.validator.annotion.NotBlank;
import com.steel.li_blog_base.validator.group.Insert;
import com.steel.li_blog_base.validator.group.Update;
import com.steel.li_blog_base.vo.BaseVO;
import lombok.Data;

/**
 * @Auther: steel
 * @Date: 2020/10/18 22:31
 * @Description:
 */
@Data
public class CategoryMenuVO extends BaseVO<CategoryMenuVO> {

    /**
     * 菜单名称
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String name;

    /**
     * 菜单级别 （一级分类，二级分类）
     */
    @IntegerNotNull(groups = {Insert.class, Update.class})
    private Integer menuLevel;

    /**
     * 菜单类型 （菜单，按钮）
     */
    @IntegerNotNull(groups = {Insert.class, Update.class})
    private Integer menuType;

    /**
     * 介绍
     */
    private String summary;

    /**
     * Icon图标
     */
    private String icon;

    /**
     * 父UID
     */
    private String parentUid;

    /**
     * URL地址
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String url;

    /**
     * 排序字段(越大越靠前)
     */
    private Integer sort;

    /**
     * 是否显示  1: 是  0: 否
     */
    @IntegerNotNull(groups = {Insert.class, Update.class})
    private Integer isShow;

}
