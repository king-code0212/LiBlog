package com.steel.li_blog_xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.steel.li_blog_base.enums.EOpenStatus;
import com.steel.li_blog_base.enums.EStatus;
import com.steel.li_blog_base.serviceImpl.SuperServiceImpl;
import com.steel.li_blog_common.entity.SystemConfig;
import com.steel.li_blog_utils.RedisUtil;
import com.steel.li_blog_utils.ResultUtil;
import com.steel.li_blog_utils.StringUtils;
import com.steel.li_blog_xo.global.MessageConf;
import com.steel.li_blog_xo.global.RedisConf;
import com.steel.li_blog_xo.global.SQLConf;
import com.steel.li_blog_xo.global.SysConf;
import com.steel.li_blog_xo.mapper.SystemConfigMapper;
import com.steel.li_blog_xo.service.SystemConfigService;
import com.steel.li_blog_xo.vo.SystemConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Auther: steel
 * @Date: 2020/10/21 21:54
 * @Description: 系统配置关系表 服务实现类
 */

@Slf4j
@Service
public class SystemConfigServiceImpl extends SuperServiceImpl<SystemConfigMapper, SystemConfig> implements SystemConfigService {

    @Autowired
    SystemConfigService systemConfigService;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public SystemConfig getConfig() {
        QueryWrapper<SystemConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(SQLConf.CREATE_TIME);
        queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
        queryWrapper.last("LIMIT 1");
        SystemConfig systemConfig = systemConfigService.getOne(queryWrapper);
        return systemConfig;
    }

    @Override
    public String cleanRedisByKey(List<String> key) {
        if (key == null) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.OPERATION_FAIL);
        }
        key.forEach(item -> {
            // 表示清空所有的Key
            if (RedisConf.ALL.equals(item)) {
                Set<String> keys = redisUtil.keys("*");
                redisUtil.delete(keys);
            } else {
                // 获取Redis中特定前缀
                Set<String> keys = redisUtil.keys(key + "*");
                redisUtil.delete(keys);
            }
        });
        return ResultUtil.result(SysConf.SUCCESS, MessageConf.OPERATION_SUCCESS);
    }

    @Override
    public String editSystemConfig(SystemConfigVO systemConfigVO) {

        if (EOpenStatus.CLOSE.equals(systemConfigVO.getUploadLocal()) && EOpenStatus.CLOSE.equals(systemConfigVO.getUploadQiNiu())) {
            return ResultUtil.result(SysConf.SUCCESS, MessageConf.PICTURE_MUST_BE_SELECT_AREA);
        }

        if (EOpenStatus.CLOSE.equals(systemConfigVO.getPicturePriority()) && EOpenStatus.CLOSE.equals(systemConfigVO.getUploadLocal())) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.MUST_BE_OPEN_LOCAL_UPLOAD);
        }

        if (EOpenStatus.OPEN.equals(systemConfigVO.getPicturePriority()) && EOpenStatus.CLOSE.equals(systemConfigVO.getUploadQiNiu())) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.MUST_BE_OPEN_QI_NIU_UPLOAD);
        }

        // 开启Email邮件通知时，必须保证Email字段不为空
        if (EOpenStatus.OPEN.equals(systemConfigVO.getStartEmailNotification()) && StringUtils.isEmpty(systemConfigVO.getEmail())) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.MUST_BE_SET_EMAIL);
        }

        if (StringUtils.isEmpty(systemConfigVO.getUid())) {
            SystemConfig systemConfig = new SystemConfig();

            // 设置七牛云相关
            systemConfig.setLocalPictureBaseUrl(systemConfigVO.getLocalPictureBaseUrl());
            systemConfig.setQiNiuPictureBaseUrl(systemConfigVO.getQiNiuPictureBaseUrl());
            systemConfig.setQiNiuAccessKey(systemConfigVO.getQiNiuAccessKey());
            systemConfig.setQiNiuSecretKey(systemConfigVO.getQiNiuSecretKey());
            systemConfig.setQiNiuBucket(systemConfigVO.getQiNiuBucket());
            systemConfig.setQiNiuArea(systemConfigVO.getQiNiuArea());
            systemConfig.setUploadLocal(systemConfigVO.getUploadLocal());
            systemConfig.setUploadQiNiu(systemConfigVO.getUploadQiNiu());
            systemConfig.setPicturePriority(systemConfigVO.getPicturePriority());

            // 设置邮箱相关
            systemConfig.setEmail(systemConfigVO.getEmail());
            systemConfig.setEmailPassword(systemConfigVO.getEmailPassword());
            systemConfig.setEmailUserName(systemConfigVO.getEmailUserName());
            systemConfig.setSmtpAddress(systemConfigVO.getSmtpAddress());
            systemConfig.setSmtpPort(systemConfigVO.getSmtpPort());
            systemConfig.setStartEmailNotification(systemConfigVO.getStartEmailNotification());
            systemConfig.insert();
        } else {
            SystemConfig systemConfig = systemConfigService.getById(systemConfigVO.getUid());
            // 设置七牛云相关
            systemConfig.setLocalPictureBaseUrl(systemConfigVO.getLocalPictureBaseUrl());
            systemConfig.setQiNiuPictureBaseUrl(systemConfigVO.getQiNiuPictureBaseUrl());
            systemConfig.setQiNiuAccessKey(systemConfigVO.getQiNiuAccessKey());
            systemConfig.setQiNiuSecretKey(systemConfigVO.getQiNiuSecretKey());
            systemConfig.setQiNiuBucket(systemConfigVO.getQiNiuBucket());
            systemConfig.setQiNiuArea(systemConfigVO.getQiNiuArea());
            systemConfig.setUploadLocal(systemConfigVO.getUploadLocal());
            systemConfig.setUploadQiNiu(systemConfigVO.getUploadQiNiu());
            systemConfig.setPicturePriority(systemConfigVO.getPicturePriority());

            // 设置邮箱相关
            systemConfig.setEmail(systemConfigVO.getEmail());
            systemConfig.setEmailPassword(systemConfigVO.getEmailPassword());
            systemConfig.setEmailUserName(systemConfigVO.getEmailUserName());
            systemConfig.setSmtpAddress(systemConfigVO.getSmtpAddress());
            systemConfig.setSmtpPort(systemConfigVO.getSmtpPort());
            systemConfig.setStartEmailNotification(systemConfigVO.getStartEmailNotification());
            systemConfig.setUpdateTime(new Date());
            systemConfig.updateById();
        }

        // 更新系统配置成功后，需要删除Redis中的系统配置，主要用于li_blog_picture获取上传配置信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if (request.getAttribute(SysConf.TOKEN) != null) {
            String token = request.getAttribute(SysConf.TOKEN).toString();
            redisUtil.delete(RedisConf.SYSTEM_CONFIG + RedisConf.SEGMENTATION + token);
            log.info("成功删除Redis中配置信息");
        }

        return ResultUtil.result(SysConf.SUCCESS, MessageConf.UPDATE_SUCCESS);
    }
}
