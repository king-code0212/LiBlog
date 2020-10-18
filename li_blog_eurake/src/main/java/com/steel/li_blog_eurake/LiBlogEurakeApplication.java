package com.steel.li_blog_eurake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LiBlogEurakeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiBlogEurakeApplication.class, args);
    }

}
