package org.jeecg.modules.demo.teacher.feign.service.impl;


import org.jeecg.modules.demo.teacher.entity.Teacher;
import org.jeecg.modules.demo.teacher.feign.service.TeacherFeign;
import org.jeecg.modules.demo.teacher.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherFeignImpl  implements TeacherFeign {

    @Autowired
    TeacherServiceImpl teacherService;

    public List<Teacher> getTeacher() {
        return teacherService.list();
    }
}
