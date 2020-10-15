package com.steel.li_blog_base.vo;

import com.steel.li_blog_base.validator.annotion.IdValid;
import com.steel.li_blog_base.validator.group.Delete;
import com.steel.li_blog_base.validator.group.Update;
import lombok.Data;

/**
 * BaseVO   view object 表现层 基类对象
 *
 * @author: steel
 * @create: 2019-12-03-22:38
 */
@Data
public class BaseVO<T> extends PageInfo<T> {

    /**
     * 唯一UID
     */
    @IdValid(groups = {Update.class, Delete.class})
    private String uid;

    private Integer status;
}
