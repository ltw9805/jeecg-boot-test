package org.jeecg.modules.cloud.feign.feign;


import org.jeecg.common.api.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "teacher-cloud-2")
@Component
public interface Teacher2FeignCLient {

    @GetMapping("/teacher/teacher2/getTeacher")
     Result<?> getTeacher();

    @GetMapping("/teacher/teacher2/button")
    Result<String> button();

}
