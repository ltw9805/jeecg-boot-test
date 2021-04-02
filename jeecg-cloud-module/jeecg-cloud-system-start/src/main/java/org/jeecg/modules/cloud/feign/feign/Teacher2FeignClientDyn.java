package org.jeecg.modules.cloud.feign.feign;

import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;

public interface Teacher2FeignClientDyn {

    @GetMapping("/teacher/teacher2/button2")
    Result<String> buttonDyn();
}
