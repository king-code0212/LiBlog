package com.steel.li_blog_xo.vo;

import com.steel.li_blog_base.validator.annotion.NotBlank;
import com.steel.li_blog_base.validator.group.Insert;
import com.steel.li_blog_base.validator.group.Update;
import com.steel.li_blog_base.vo.BaseVO;
import lombok.Data;

/**
 * @Auther: steel
 * @Date: 2020/10/18 22:27
 * @Description:
 */
@Data
public class BlogSortVO extends BaseVO<BlogSortVO> {

    /**
     * 分类名
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String sortName;

    /**
     * 分类介绍
     */
    private String content;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 无参构造方法
     */
    BlogSortVO() {

    }
}
