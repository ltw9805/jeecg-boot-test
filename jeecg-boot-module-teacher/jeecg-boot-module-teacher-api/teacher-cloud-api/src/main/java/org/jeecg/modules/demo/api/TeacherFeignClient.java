package  org.jeecg.modules.demo.api;

import org.jeecg.common.api.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "teacher-cloud")
@Component
public interface TeacherFeignClient {

    @GetMapping("/teacher/teacher/button")
    Result<String> button();

    @GetMapping("/teacher/teacher/getTeacher")
    Result<?> getTeacherList();

}
