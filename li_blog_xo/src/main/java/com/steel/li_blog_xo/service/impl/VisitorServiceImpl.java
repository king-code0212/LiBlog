package com.steel.li_blog_xo.service.impl;


import com.steel.li_blog_base.serviceImpl.SuperServiceImpl;
import com.steel.li_blog_common.entity.Visitor;
import com.steel.li_blog_xo.mapper.VisitorMapper;
import com.steel.li_blog_xo.service.VisitorService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博主表 服务实现类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-08
 */
@Service
public class VisitorServiceImpl extends SuperServiceImpl<VisitorMapper, Visitor> implements VisitorService {

}
