package org.jeecg.modules.demo.stu_test.feign.service.impl;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.stu_test.feign.service.feignTest;
import org.springframework.stereotype.Service;

@Service
public class FeignTestImpl  implements feignTest {
    @Override
    public Result<String> feignTest(String name) {
        return Result.OK("hello"+name);
    }
}
