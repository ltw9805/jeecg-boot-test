package org.jeecg.modules.demo.autocore.feign;


import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autocore/demandManagement2")
public class feignDMController {

    @GetMapping("/button")
    public Result<String> button(){
        return Result.OK("需求管理Feign测试成功！");
    }
}
