package  org.jeecg.modules.demo.api;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.api.fallback.JeecgHelloFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "jeecg-demo", fallbackFactory = JeecgHelloFallback.class)
public interface JeecgHelloApi {

    /**
     * 根据service_path获取api配置信息
     * @param
     * @return
     */
    @GetMapping(value = "/test/getMessage")
    Result<String> getMessage(@RequestParam(value = "name") String name);
}
