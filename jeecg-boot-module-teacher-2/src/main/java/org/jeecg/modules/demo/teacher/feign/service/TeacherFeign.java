package org.jeecg.modules.demo.teacher.feign.service;


import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.teacher.entity.Teacher;

import java.util.List;

public interface TeacherFeign   {
    List<Teacher> getTeacher();
    Result<String> button(String str);
    Result<String> buttonDyn(String str);
}
