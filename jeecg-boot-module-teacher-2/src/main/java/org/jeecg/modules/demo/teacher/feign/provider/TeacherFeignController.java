package org.jeecg.modules.demo.teacher.feign.provider;


import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.teacher.feign.service.impl.TeacherFeignImpl;
import org.jeecg.modules.demo.teacher.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher/teacher2")
public class TeacherFeignController {

    @Autowired
    private ITeacherService teacherService;

    @Autowired
    TeacherFeignImpl teacherFeign;


    @GetMapping("/getTeacher")
    public Result<?> getTeacher(){
        return Result.OK(teacherFeign.getTeacher());
    }

    @GetMapping("/button")
    public Result<String> button(){
        return teacherFeign.button("Feign teacher2 test success.");
    }
    @GetMapping("/button2")
    public Result<String> buttonDyn2(){
        return teacherFeign.buttonDyn("Feign teacher2 dyn test success.");
    }
}
