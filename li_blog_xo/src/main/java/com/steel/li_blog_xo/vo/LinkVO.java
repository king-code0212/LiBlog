package com.steel.li_blog_xo.vo;


import com.steel.li_blog_base.validator.annotion.NotBlank;
import com.steel.li_blog_base.validator.group.Insert;
import com.steel.li_blog_base.validator.group.Update;
import com.steel.li_blog_base.vo.BaseVO;
import lombok.Data;

/**
 * LinkVO
 *
 * @author: steel
 * @create: 2019年12月11日12:41:28
 */
@Data
public class LinkVO extends BaseVO<LinkVO> {

    /**
     * 友链标题
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String title;
    /**
     * 友链介绍
     */
    private String summary;
    /**
     * 友链地址
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String url;

    /**
     * 友链状态： 0 申请中， 1：已上线，  2：已拒绝
     */
    private Integer linkStatus;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 无参构造方法，初始化默认值
     */
    LinkVO() {

    }

}
