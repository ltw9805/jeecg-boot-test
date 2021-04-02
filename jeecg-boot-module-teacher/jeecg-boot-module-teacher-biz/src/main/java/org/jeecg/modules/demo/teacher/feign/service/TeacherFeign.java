package org.jeecg.modules.demo.teacher.feign.service;


import org.jeecg.modules.demo.teacher.entity.Teacher;

import java.util.List;

public interface TeacherFeign   {
    List<Teacher> getTeacher();
}
