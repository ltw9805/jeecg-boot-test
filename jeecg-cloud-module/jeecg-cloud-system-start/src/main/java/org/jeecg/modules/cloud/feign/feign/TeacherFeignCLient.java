package org.jeecg.modules.cloud.feign.feign;


import org.jeecg.common.api.vo.Result;

import org.jeecg.modules.cloud.feign.entiy.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "teacher-cloud")
@Component
public interface TeacherFeignCLient {

    @GetMapping("/teacher/teacher/getTeacher")
     Result<?> getTeacher();

    @GetMapping("/teacher/teacher/button")
    Result<String> button();



}
