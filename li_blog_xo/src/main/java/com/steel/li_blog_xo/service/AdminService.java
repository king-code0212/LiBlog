package com.steel.li_blog_xo.service;

import com.steel.li_blog_base.service.SuperService;
import com.steel.li_blog_common.entity.Admin;
import com.steel.li_blog_xo.vo.AdminVO;

/**
 * @Auther: steel
 * @Date: 2020/10/21 15:07
 * @Description: 管理员表 服务类
 */
public interface AdminService extends SuperService<Admin> {
    /**
     * 通过UID获取Admin
     *
     * @param uid
     * @return
     */
    public Admin getAdminByUid(String uid);

    /**
     * Web端通过用户名获取一个Admin
     *
     * @param userName
     * @return
     */
    public Admin getAdminByUser(String userName);


    /**
     * 获取当前管理员
     *
     * @return
     */
    public Admin getMe();

    /**
     * 添加在线用户
     * @param admin
     * @return
     */
    public void addOnlineAdmin(Admin admin);

    /**
     * 编辑当前管理员信息
     *
     * @return
     */
    public String editMe(AdminVO adminVO);

    /**
     * 修改密码
     *
     * @return
     */
    public String changePwd(String oldPwd, String newPwd);
}
