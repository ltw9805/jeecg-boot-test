package org.jeecg.modules.demo.stu_test;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroBeanAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(exclude = {ShiroAnnotationProcessorAutoConfiguration.class,
        ShiroAutoConfiguration.class,
        ShiroBeanAutoConfiguration.class})
public class JeecgBootTest extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(JeecgBootTest.class, args);

    }
}

