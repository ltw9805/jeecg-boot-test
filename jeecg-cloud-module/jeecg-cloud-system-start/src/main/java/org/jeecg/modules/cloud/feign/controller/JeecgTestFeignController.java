package org.jeecg.modules.cloud.feign.controller;


import cn.hutool.core.util.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.boot.starter.rabbitmq.client.RabbitMqClient;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.base.BaseMap;
//import org.jeecg.common.system.api.IDamandMApi;
import org.jeecg.modules.cloud.constant.CloudConstant;
//import org.jeecg.modules.cloud.feign.feign.FeignTestClient;
import org.jeecg.modules.cloud.feign.feign.*;

import org.jeecg.modules.demo.api.TeacherFeignCLient;
import org.jeecg.modules.demo.api.TeacherFeignClientDyn;
import org.jeecg.starter.cloud.feign.impl.JeecgFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sys/test")
@Api(tags = "【微服务】单元测试")
public class JeecgTestFeignController {

    @Autowired
    private JeecgFeignService jeecgFeignService;
    @Autowired
    private JeecgTestClient jeecgTestClient;
    @Autowired
    private RabbitMqClient rabbitMqClient;

    @Autowired
    TeacherFeignCLient teacherFeignCLient;

    @Autowired
    Teacher2FeignCLient teacher2FeignCLient;

//    @Autowired
//    IDamandMApi IDamandMApi;



    // cloud-api 中的feign调用
//    @GetMapping("/feign/demandManagement/button")
//    Result<String> buttonDm(){
//
//        return  IDamandMApi.button();
//    }



    @GetMapping("/feign/teacher/button")
    @ApiOperation(value = "测试teacher的button-feign", notes = "测试teacher的button-feign")
    public Result<String> button(){
        return teacherFeignCLient.button();
    }

    @GetMapping("/feign/teacher/button2")
    @ApiOperation(value = "测试teacher的button-动态feign", notes = "测试teacher的button-动态feign")
    public Result<String> button2(){
        TeacherFeignClientDyn teacherFeignClientDyn =  jeecgFeignService.newInstance(TeacherFeignClientDyn.class, "teacher-cloud");
        return teacherFeignClientDyn.buttonDyn();
    }

    @GetMapping("/feign/teacher2/button")
    @ApiOperation(value = "测试teacher2的button-feign", notes = "测试teacher2的button-feign")
    public Result<String> button3(){
        return teacher2FeignCLient.button();
    }

    @GetMapping("/feign/teacher2/button2")
    @ApiOperation(value = "测试teacher2的button-动态feign", notes = "测试teacher2的button-动态feign")
    public Result<String> button4(){
        Teacher2FeignClientDyn teacherFeignClientDyn =  jeecgFeignService.newInstance(Teacher2FeignClientDyn.class, "teacher-cloud-2");
        return teacherFeignClientDyn.buttonDyn();
    }


//    @GetMapping("/feign/teacher")
//    @ApiOperation(value = "测试学生的feign", notes = "测试学生的feign")
//    public Result<?> getTeacher() {
//        return teacherFeignCLient.getTeacher();
//    }


    @GetMapping("getMessage")
    @ApiOperation(value = "测试feign", notes = "测试feign")
    public Result<String> getMessage() {
        return jeecgTestClient.getMessage("jeecg-boot");
    }

    @GetMapping("getMessage2")
    @ApiOperation(value = "测试动态feign", notes = "测试动态feign")
    public Result<String> getMessage2() {
        JeecgTestClientDyn myClientDyn = jeecgFeignService.newInstance(JeecgTestClientDyn.class, CloudConstant.SERVER_NAME_JEECGDEMO);
        return myClientDyn.getMessage("动态fegin——jeecg-boot2");
    }

    @GetMapping(value = "/rabbitmq")
    @ApiOperation(value = "测试rabbitmq", notes = "测试rabbitmq")
    public Result<?> rabbitMqClientTest(HttpServletRequest req) {
        //rabbitmq消息队列测试
        BaseMap map = new BaseMap();
        map.put("orderId", RandomUtil.randomNumbers(10));
        rabbitMqClient.sendMessage(CloudConstant.MQ_JEECG_PLACE_ORDER, map);
        rabbitMqClient.sendMessage(CloudConstant.MQ_JEECG_PLACE_ORDER_TIME, map,10);

        //rabbitmq消息总线测试
        BaseMap params = new BaseMap();
        params.put("orderId", "123456");
        rabbitMqClient.publishEvent(CloudConstant.MQ_DEMO_BUS_EVENT, params);
        return Result.OK("MQ发送消息成功");
    }
}
