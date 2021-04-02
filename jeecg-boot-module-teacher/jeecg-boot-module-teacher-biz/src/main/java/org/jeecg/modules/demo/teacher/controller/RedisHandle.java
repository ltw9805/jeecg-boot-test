package org.jeecg.modules.demo.teacher.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.base.BaseMap;
import org.jeecg.common.modules.redis.listener.JeecgRedisListerer;
import org.jeecg.modules.demo.teacher.entity.Teacher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisHandle implements JeecgRedisListerer {
    @Override
    public void onMessage(BaseMap message) {
        IPage<Teacher> pageList=message.get("teacher-list");
        System.out.println("==================="+pageList.toString());
    }
}
