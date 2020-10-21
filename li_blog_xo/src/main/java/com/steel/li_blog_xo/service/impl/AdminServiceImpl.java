package com.steel.li_blog_xo.service.impl;

import com.steel.li_blog_base.service.SuperService;
import com.steel.li_blog_base.serviceImpl.SuperServiceImpl;
import com.steel.li_blog_common.entity.Admin;
import com.steel.li_blog_xo.mapper.AdminMapper;
import com.steel.li_blog_xo.service.AdminService;
import com.steel.li_blog_xo.vo.AdminVO;
import org.springframework.stereotype.Service;

/**
 * @Auther: steel
 * @Date: 2020/10/21 15:08
 * @Description:
 */
@Service
public class AdminServiceImpl extends SuperServiceImpl<AdminMapper, Admin> implements AdminService {



    @Override
    public Admin getAdminByUid(String uid) {
        return null;
    }

    @Override
    public Admin getAdminByUser(String userName) {
        return null;
    }

    @Override
    public Admin getMe() {
        return null;
    }

    @Override
    public void addOnlineAdmin(Admin admin) {

    }

    @Override
    public String editMe(AdminVO adminVO) {
        return null;
    }

    @Override
    public String changePwd(String oldPwd, String newPwd) {
        return null;
    }
}
