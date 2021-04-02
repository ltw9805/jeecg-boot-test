package org.jeecg.modules.api.controller;


import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {


    @GetMapping("/hello")
    public Result<String> hello() {
        Result<String> result = new Result<>();

        result.setResult("hello world");
        result.setSuccess(true);

        return result;


    }
}
