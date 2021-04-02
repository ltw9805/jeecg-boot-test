//package org.jeecg.modules.demo.teacher.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import org.jeecg.common.api.vo.Result;
//import org.jeecg.common.base.BaseMap;
//import org.jeecg.common.modules.redis.client.JeecgRedisClient;
//import org.jeecg.common.system.query.QueryGenerator;
//import org.jeecg.modules.demo.teacher.entity.Teacher;
//import org.jeecg.modules.demo.teacher.service.ITeacherService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.support.collections.RedisList;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestController
//@RequestMapping("/teacher/teacher/redis")
//public class MyRedissonController {
//
//    @Autowired
//    private ITeacherService teacherService;
//
//    @Autowired
//    JeecgRedisClient redisClient;
//
//
//
//
//    @GetMapping(value = "/list")
//    public Result<?> queryPageList(Teacher teacher,
//                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//                                   HttpServletRequest req) {
//        QueryWrapper<Teacher> queryWrapper = QueryGenerator.initQueryWrapper(teacher, req.getParameterMap());
//        Page<Teacher> page = new Page<Teacher>(pageNo, pageSize);
//        IPage<Teacher> pageList = teacherService.page(page, queryWrapper);
//
//        BaseMap map = new BaseMap();
//        map.put("teacher-list",pageList);
//        redisClient.sendMessage("redisHandle",map);
//        return Result.OK("pageList");
//    }
//
//
//}
