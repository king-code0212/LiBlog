package com.steel.li_blog_xo.service;

import com.steel.li_blog_base.service.SuperService;
import com.steel.li_blog_common.entity.SystemConfig;
import com.steel.li_blog_xo.vo.SystemConfigVO;

import java.util.List;

/**
 * @Auther: steel
 * @Date: 2020/10/21 21:49
 * @Description: 系统配置表 服务类
 */
public interface SystemConfigService extends SuperService<SystemConfig> {

    /**
     *
     * 功能描述: 获取系统配置
     *
     * @return:
     */
    public SystemConfig getConfig();


    /**
     *
     * 功能描述: 通过key前缀清空Redis缓存
     *
     * @return:
     */
    public String cleanRedisByKey(List<String> key);


    /**
     *
     * 功能描述: 修改系统配置
     *
     * @return:
     */
    public String editSystemConfig(SystemConfigVO systemConfigVO);

}
