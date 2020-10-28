package com.steel.li_blog_xo.service;

import com.steel.li_blog_base.service.SuperService;
import com.steel.li_blog_common.entity.WebConfig;
import com.steel.li_blog_xo.vo.WebConfigVO;

/**
 * @Auther: steel
 * @Date: 2020/10/28 21:31
 * @Description: 网站配置表 服务类
 */
public interface WebConfigService extends SuperService<WebConfig> {

    /**
     * 获取网站配置
     *
     * @return
     */
    public WebConfig getWebConfig();

    /**
     * 通过显示列表获取配置
     *
     * @return
     */
    public WebConfig getWebConfigByShowList();

    /**
     * 修改网站配置
     *
     * @param webConfigVO
     * @return
     */
    public String editWebConfig(WebConfigVO webConfigVO);
}
