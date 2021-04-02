package org.jeecg.modules.demo.student.redis;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.demo.student.entity.Student;
import org.jeecg.modules.demo.student.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/student/student/redis")
public class RedisController {

    @Autowired
    private IStudentService studentService;

    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/list")
    public Result<?> getStudent(Student student,
                                @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                HttpServletRequest req){

        Result result = (Result) redisUtil.get("student-list");

        if(result != null){
            return result;

        }else {
            QueryWrapper<Student> queryWrapper = QueryGenerator.initQueryWrapper(student, req.getParameterMap());
            Page<Student> page = new Page<Student>(pageNo, pageSize);
            IPage<Student> pageList = studentService.page(page, queryWrapper);
            Result studentList = Result.OK(pageList);
            redisUtil.set("student-list",studentList,1000);
            return studentList;
        }




    }

}
