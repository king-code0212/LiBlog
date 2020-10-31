package com.steel.li_blog_picture.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.steel.li_blog_base.entity.SuperEntity;
import lombok.Data;

/**
 * @Auther: steel
 * @Date: 2020/10/31 15:04
 * @Description: 文件实体类
 */
@TableName("t_file")
@Data
public class File extends SuperEntity<File> {

    private static final long serialVersionUID = 1L;

    private String fileOldName;

    private Long fileSize;

    private String fileSortUid;

    /**
     * 图片扩展名
     */
    private String picExpandedName;

    /**
     * 图片名称
     */
    private String picName;

    /**
     * 图片url地址
     */
    private String picUrl;

    /**
     * 管理员Uid
     */
    private String adminUid;

    /**
     * 用户Uid
     */
    private String userUid;

    /**
     * 七牛云Url
     */
    private String qiNiuUrl;
}
