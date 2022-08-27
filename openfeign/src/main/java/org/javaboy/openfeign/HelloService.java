package org.javaboy.openfeign;

import org.javaboy.api.IUserService;
import org.javaboy.commons.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author szh
 * @Date 2022/5/29 16:34
 * @PackageName:org.javaboy.openfeign
 * @ClassName: HelloService
 * @Description: TODO
 * @Version 1.0
 */
/*与provider服务绑定*/             //这里配置了服务降级类
@FeignClient(value = "provider",fallbackFactory = HelloServiceFallBackFactory.class)
public interface HelloService extends IUserService {
}
