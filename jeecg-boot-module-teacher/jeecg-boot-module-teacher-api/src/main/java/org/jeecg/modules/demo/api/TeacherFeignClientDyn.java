package org.jeecg.modules.demo.api;

import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;

public interface TeacherFeignClientDyn {

    @GetMapping("/teacher/teacher/button2")
    Result<String> buttonDyn();
}