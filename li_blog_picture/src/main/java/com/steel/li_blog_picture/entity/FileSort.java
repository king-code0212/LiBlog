package com.steel.li_blog_picture.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.steel.li_blog_base.entity.SuperEntity;
import lombok.Data;

/**
 * @Auther: steel
 * @Date: 2020/10/31 15:05
 * @Description:
 */
@TableName("t_file_sort")
@Data
public class FileSort extends SuperEntity<FileSort> {

    private static final long serialVersionUID = 1L;

    private String projectName;

    private String sortName;

    private String url;

}

