package com.steel.li_blog_xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steel.li_blog_base.service.SuperService;
import com.steel.li_blog_common.entity.Role;
import com.steel.li_blog_xo.vo.RoleVO;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-04
 */
public interface RoleService extends SuperService<Role> {

    /**
     * 获取角色列表
     *
     * @param roleVO
     * @return
     */
    public IPage<Role> getPageList(RoleVO roleVO);

    /**
     * 新增角色
     *
     * @param roleVO
     */
    public String addRole(RoleVO roleVO);

    /**
     * 编辑角色
     *
     * @param roleVO
     */
    public String editRole(RoleVO roleVO);

    /**
     * 删除角色
     *
     * @param roleVO
     */
    public String deleteRole(RoleVO roleVO);

}
