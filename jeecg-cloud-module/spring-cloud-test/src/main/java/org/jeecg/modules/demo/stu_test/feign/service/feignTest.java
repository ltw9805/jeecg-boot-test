package org.jeecg.modules.demo.stu_test.feign.service;

import org.jeecg.common.api.vo.Result;

public interface feignTest {

    Result<String> feignTest(String name);
}
