package com.steel.li_blog_common.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: steel
 * @Date: 2020/10/18 14:26
 * @Description: Feign请求拦截器（设置请求头，传递登录信息）
 */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 获取token，放入到feign的请求头
        String token = null;
        if(request.getParameter("token") != null) {
            token = request.getParameter("token");
        } else if(request.getAttribute("token") != null) {
            token = request.getAttribute("token").toString();
        }

        if(StringUtils.isNotEmpty(token)){
            // 如果带有？说明还带有其它参数，我们只截取到token即可
            if(token.indexOf("?") != -1) {
                String [] params = token.split("\\?url=");
                token = params[0];
            }
            requestTemplate.header("pictureToken", token);
        }
    }

}
