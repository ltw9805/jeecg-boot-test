package org.jeecg.modules.demo.student.feign.service;

import org.jeecg.common.api.vo.Result;

public interface StudentFeignTest {

    Result<String> StuFeignTest(String name);

}