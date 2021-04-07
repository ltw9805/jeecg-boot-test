package org.jeecg.common.system.api;


import org.jeecg.common.api.CommonAPI;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.ServiceNameConstants;
import org.jeecg.common.system.api.factory.SysBaseAPIFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(contextId = "demandMangementFeignApi", value = "demand-mangement", fallbackFactory = SysBaseAPIFallbackFactory.class)
public interface IDamandMApi  {

//    @GetMapping("/autocore/demandManagement2/button")
//    Result<?> getTeacher();

    @GetMapping("/autocore/demandManagement2/button")
    Result<String> button();

}
