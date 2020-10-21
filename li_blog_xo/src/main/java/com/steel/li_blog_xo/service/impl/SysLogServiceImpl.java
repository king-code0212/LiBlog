package com.steel.li_blog_xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.steel.li_blog_base.enums.EStatus;
import com.steel.li_blog_base.serviceImpl.SuperServiceImpl;
import com.steel.li_blog_common.entity.SysLog;
import com.steel.li_blog_utils.DateUtils;
import com.steel.li_blog_utils.StringUtils;
import com.steel.li_blog_xo.global.SQLConf;
import com.steel.li_blog_xo.global.SysConf;
import com.steel.li_blog_xo.mapper.SysLogMapper;
import com.steel.li_blog_xo.service.SysLogService;
import com.steel.li_blog_xo.vo.SysLogVO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.crypto.Data;

/**
 * @Auther: steel
 * @Date: 2020/10/21 22:45
 * @Description: 操作日志 服务实现类
 */
public class SysLogServiceImpl extends SuperServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    SysLogService sysLogService;

    @Override
    public IPage<SysLog> getPageList(SysLogVO sysLogVO) {

        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sysLogVO.getUserName()) && !StringUtils.isEmpty(sysLogVO.getUserName().trim())) {
            queryWrapper.like(SQLConf.USER_NAME, sysLogVO.getUserName().trim());
        }

        if (!StringUtils.isEmpty(sysLogVO.getOperation())) {
            queryWrapper.like(SQLConf.OPERATION, sysLogVO.getOperation());
        }

        if (!StringUtils.isEmpty(sysLogVO.getStartTime())) {
            String[] time = sysLogVO.getStartTime().split(SysConf.FILE_SEGMENTATION);
            if (time.length == 2) {
                queryWrapper.between(SQLConf.CREATE_TIME, DateUtils.str2Date(time[0]), DateUtils.str2Date(time[1]));
            }
        }

        Page<SysLog> page = new Page<>();
        page.setCurrent(sysLogVO.getCurrentPage());
        page.setSize(sysLogVO.getPageSize());
        queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
        queryWrapper.orderByDesc(SQLConf.CREATE_TIME);
        IPage<SysLog> pageList = sysLogService.page(page, queryWrapper);

        return pageList;
    }
}
