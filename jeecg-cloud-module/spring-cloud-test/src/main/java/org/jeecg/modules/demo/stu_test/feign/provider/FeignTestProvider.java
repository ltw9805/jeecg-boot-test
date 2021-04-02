package org.jeecg.modules.demo.stu_test.feign.provider;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.stu_test.feign.service.impl.FeignTestImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stu_test/studentTest")
public class FeignTestProvider {
    @Autowired
    FeignTestImpl feignTestImpl;


    @GetMapping("/test/feign")
    public Result<String> feignTest(@RequestParam("name") String name){
        return  feignTestImpl.feignTest(name);
    }
}
