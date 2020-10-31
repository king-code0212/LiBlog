package com.steel.li_blog_picture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients("com.steel.li_blog_common.feign")
@ComponentScan(basePackages = {
        "com.steel.li_blog_common.config.feign",
        "com.steel.li_blog_common.config.redis",
        "com.steel.li_blog_picture.config",
        "com.steel.li_blog_utils",
        "com.steel.li_blog_picture.util",
        "com.steel.li_blog_picture.restapi",
        "com.steel.li_blog_picture.service"})
public class LiBlogPictureApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiBlogPictureApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //配置允许跨域访问的路径
                registry.addMapping("/**/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .exposedHeaders("")
                        .maxAge(3600);
            }
        };
    }

}
