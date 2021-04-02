package org.jeecg.modules.cloud.feign.feign;


import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cloud.feign.feign.fallback.JeecgTestClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//去nacos中找 名字为 student-cloud 的服务
@FeignClient(value = "student-cloud", fallbackFactory = JeecgTestClientFallback.class)
@Component
public interface StudentTestClient {
    @GetMapping(value = "/student/student/test/feign")
    Result<String> StuFeignTest(@RequestParam("name") String name);
}
