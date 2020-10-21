package com.steel.li_blog_xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steel.li_blog_base.service.SuperService;
import com.steel.li_blog_common.entity.SysLog;
import com.steel.li_blog_xo.vo.SysLogVO;

/**
 * @Auther: steel
 * @Date: 2020/10/21 22:45
 * @Description: 操作日志服务类
 */
public interface SysLogService extends SuperService<SysLog> {

    /**
     *
     * 功能描述: 获取操作日志列表
     *
     * @return:
     */
    public IPage<SysLog> getPageList(SysLogVO sysLogVO);
}
