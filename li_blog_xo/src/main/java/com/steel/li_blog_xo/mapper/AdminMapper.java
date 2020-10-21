package com.steel.li_blog_xo.mapper;

import com.steel.li_blog_base.mapper.SuperMapper;
import com.steel.li_blog_common.entity.Admin;
import org.apache.ibatis.annotations.Param;

/**
 * @Auther: steel
 * @Date: 2020/10/21 14:24
 * @Description: 管理员 Mapper 接口
 */
public interface AdminMapper extends SuperMapper<Admin> {



   /**
    * 通过uid获取管理员
    *
    * @return
    */
    public Admin getAdminByUid(@Param("uid") String uid);
}
