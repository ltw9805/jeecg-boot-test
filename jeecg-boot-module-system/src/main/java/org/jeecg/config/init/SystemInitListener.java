package org.jeecg.config.init;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.config.JeecgCloudCondition;
import org.jeecg.modules.system.service.ISysGatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @desc: 启动程序，初始化路由配置,
 *          实现 ApplicationListener<>  泛型为：ApplicationReadyEvent，意思为在上下文已经准备就绪完毕的时候触发
 *          即在上下文已经准备就绪完毕的时候触发监听器SystemInitListener，
 *          实现的Ordered 是用来排序的，指定实现了相同接口的实现类的优先级，例如多个监听器，排序
 * @author: flyme
 */
@Slf4j
@Component
@Conditional(JeecgCloudCondition.class)
public class SystemInitListener implements ApplicationListener<ApplicationReadyEvent>, Ordered {


    @Autowired
    private ISysGatewayRouteService sysGatewayRouteService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        log.info(" 服务已启动，初始化路由配置 ###################");
        log.info(" 初始化：即将jeecg-boot数据库的sys_gateway_route表数据作为value放入Redis，key为：sys:cache:cloud:gateway_routes，原理是使用Spring的监听器");
        if (applicationReadyEvent.getApplicationContext().getDisplayName().indexOf("AnnotationConfigServletWebServerApplicationContext") > -1) {
            sysGatewayRouteService.addRoute2Redis(CacheConstant.GATEWAY_ROUTES);
        }

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
