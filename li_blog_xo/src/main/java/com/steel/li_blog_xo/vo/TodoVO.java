package com.steel.li_blog_xo.vo;

import com.steel.li_blog_base.validator.annotion.BooleanNotNULL;
import com.steel.li_blog_base.validator.annotion.NotBlank;
import com.steel.li_blog_base.validator.group.GetOne;
import com.steel.li_blog_base.validator.group.Insert;
import com.steel.li_blog_base.validator.group.Update;
import com.steel.li_blog_base.vo.BaseVO;
import lombok.Data;

/**
 * TodoVO
 *
 * @author: steel
 * @create: 2019年12月18日22:16:23
 */
@Data
public class TodoVO extends BaseVO<TodoVO> {

    /**
     * 内容
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String text;
    /**
     * 表示事项是否完成
     */
    @BooleanNotNULL(groups = {Update.class, GetOne.class})
    private Boolean done;


    /**
     * 无参构造方法，初始化默认值
     */
    TodoVO() {

    }

}
