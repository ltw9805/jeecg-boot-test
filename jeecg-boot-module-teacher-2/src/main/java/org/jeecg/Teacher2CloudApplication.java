package org.jeecg;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
//(exclude = {ShiroAnnotationProcessorAutoConfiguration.class,
//        ShiroAutoConfiguration.class,
//        ShiroBeanAutoConfiguration.class})
@SpringBootApplication
@EnableFeignClients
public class Teacher2CloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(Teacher2CloudApplication.class, args);
    }

}
