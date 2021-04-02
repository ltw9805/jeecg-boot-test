package org.jeecg.modules.demo.teacher.rabbitMq;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.boot.starter.lock.annotation.JLock;
import org.jeecg.boot.starter.rabbitmq.client.RabbitMqClient;
import org.jeecg.common.base.BaseMap;
import org.jeecg.modules.demo.teacher.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class SendClient {

    @Autowired
    private RabbitMqClient rabbitMqClient;
    //没5s自动执行一次sendMessage（）
//    @Scheduled(cron = "0/3 * * * * ?")
    public  void sendMess() throws InterruptedException{
        BaseMap map = new BaseMap();
        Teacher teacher = new Teacher();
        teacher.setTeacherAge(15);
        teacher.setTeacherName("AAAAAA");
        teacher.setTeacherSex(1);
        System.out.println("=======================业务逻辑2=============================");
        map.put("orderId",teacher);

        // 向 "jeecg_place_order"这个quenes（队列）,发送消息map
        // quene没有就会自动创建，并且绑定到jeecg.direct.exchange这个交换机上，
        rabbitMqClient.sendMessage("jeecg_place_order_2",map);
        //延迟10秒发送
        map.put("orderId", "22222222");
        rabbitMqClient.sendMessage("jeecg_place_order_2", map, 1000);
    }
}
