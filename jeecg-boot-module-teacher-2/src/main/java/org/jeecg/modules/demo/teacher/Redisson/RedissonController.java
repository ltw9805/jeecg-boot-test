package org.jeecg.modules.demo.teacher.Redisson;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.base.BaseMap;
import org.jeecg.common.modules.redis.client.JeecgRedisClient;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.demo.teacher.entity.Teacher;
import org.jeecg.modules.demo.teacher.service.ITeacherService;
import org.jetbrains.annotations.NotNull;
//import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import org.jeecg.boot.starter.lock.annotation.JLock;
@RestController
@RequestMapping("/teacher/teacher2/redis")
public class RedissonController {

    @Autowired
    private ITeacherService teacherService;

    @Autowired
    JeecgRedisClient redisClient;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;
//    /actuator/metrics


    @JLock(lockKey = "demoLockKey1")
    @GetMapping(value = "/list")
    @PermissionData(pageComponent = "teacher2/TeacherList")
    public Result<?> queryPageList(Teacher teacher,
                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   HttpServletRequest req) {


        return method2(teacher, pageNo, pageSize, req);


//        return method2(teacher, pageNo, pageSize, req);
    }

    @NotNull
    private Result<IPage<Teacher>> method1(Teacher teacher, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        //????????? ???
        // ????????????
        IPage<Teacher> teacherList = (IPage<Teacher>) redisUtil.get("teacher-list");


        // redis??????????????????redis??????
        if (teacherList != null){
            System.out.println("????????? ???redis?????????");
            return Result.OK(teacherList);
        }
        // redis?????????????????????????????????????????????redis????????????????????????
        else {
            QueryWrapper<Teacher> queryWrapper = QueryGenerator.initQueryWrapper(teacher, req.getParameterMap());
            Page<Teacher> page = new Page<Teacher>(pageNo, pageSize);
            IPage<Teacher> pageList = teacherService.page(page, queryWrapper);

            redisUtil.set("teacher-list",pageList,5);
            System.out.println("????????? ????????????redis");
            return Result.OK(pageList);
        }
    }

    @NotNull
    private Result method2(Teacher teacher, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        //????????? ???
        // ????????????
        Result teacherList = (Result) redisUtil.get("teacher-list");

        // redis??????????????????redis??????
        if (teacherList != null){
            System.out.println("????????? ???redis?????????");
            return teacherList;
        }
        // redis?????????????????????????????????????????????redis????????????????????????
        else {
            QueryWrapper<Teacher> queryWrapper = QueryGenerator.initQueryWrapper(teacher, req.getParameterMap());
            Page<Teacher> page = new Page<Teacher>(pageNo, pageSize);
            IPage<Teacher> pageList = teacherService.page(page, queryWrapper);

            Result result = Result.OK(pageList);
            redisUtil.set("teacher-list",result,200);
            System.out.println("????????? ????????????redis");
            return Result.OK(pageList);

        }
    }
}

