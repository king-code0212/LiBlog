package com.steel.li_blog_xo.service.impl;


import com.steel.li_blog_base.serviceImpl.SuperServiceImpl;
import com.steel.li_blog_common.entity.CommentReport;
import com.steel.li_blog_xo.mapper.CommentReportMapper;
import com.steel.li_blog_xo.service.CommentReportService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论举报表 服务实现类
 * </p>
 *
 * @author 陌溪
 * @since 2020年1月12日15:47:47
 */
@Service
public class CommentReportServiceImpl extends SuperServiceImpl<CommentReportMapper, CommentReport> implements CommentReportService {

}
