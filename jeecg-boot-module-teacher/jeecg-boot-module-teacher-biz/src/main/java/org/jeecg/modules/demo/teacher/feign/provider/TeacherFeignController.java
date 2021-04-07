package org.jeecg.modules.demo.teacher.feign.provider;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.teacher.entity.Teacher;
import org.jeecg.modules.demo.teacher.feign.service.impl.TeacherFeignImpl;
import org.jeecg.modules.demo.teacher.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/teacher/teacher")
public class TeacherFeignController {
//
//    @Autowired
//    private ITeacherService teacherService;
//
//
//    @Autowired
//    TeacherFeignImpl TeacherFeign;
//
//    @GetMapping("/getTeacher")
//    public Result<?> getTeacher(){
//        return Result.OK(TeacherFeign.getTeacher());
//
//    }

    @GetMapping("/button")
    public Result<String> button(){
        return Result.OK("Feign test success.");
    }
    @GetMapping("/button2")
    public Result<String> buttonDyn(){
        return Result.OK("Feign dyn test success.");
    }
}
