package org.jeecg.modules.demo.student.feign.controller;


import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.student.feign.service.impl.StudentFeignTestImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student/student")
public class StudentFeignTestController {

    @Autowired
    StudentFeignTestImpl studentFeignTestImpl;


    @GetMapping("/test/feign")
    public Result<String> StudentFeignTest(@RequestParam String name){

        return studentFeignTestImpl.StuFeignTest(name);
    }
}
