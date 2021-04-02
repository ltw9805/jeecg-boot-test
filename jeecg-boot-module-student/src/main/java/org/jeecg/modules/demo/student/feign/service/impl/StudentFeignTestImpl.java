package org.jeecg.modules.demo.student.feign.service.impl;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.student.feign.service.StudentFeignTest;
import org.springframework.stereotype.Service;

@Service
public class StudentFeignTestImpl implements StudentFeignTest {
    @Override
    public Result<String> StuFeignTest(String name) {
        return  Result.OK("Hello，" + name);
    }
}
