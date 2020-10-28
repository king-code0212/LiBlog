package com.steel.li_blog_xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steel.li_blog_base.service.SuperService;
import com.steel.li_blog_common.entity.ExceptionLog;
import com.steel.li_blog_xo.vo.ExceptionLogVO;


/**
 * <p>
 * 操作异常日志 服务类
 * </p>
 *
 * @author limbo
 * @since 2018-09-30
 */
public interface ExceptionLogService extends SuperService<ExceptionLog> {

    /**
     * 获取异常日志列表
     *
     * @param exceptionLogVO
     * @return
     */
    public IPage<ExceptionLog> getPageList(ExceptionLogVO exceptionLogVO);
}
