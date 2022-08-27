package org.javaboy.nacos.nacosconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author szh
 * @Date 2022/7/11 20:16
 * @PackageName:org.javaboy.nacos.nacosconfig
 * @ClassName: HelloController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
/*动态刷新*/
@RefreshScope
public class HelloController {

    @Value("${name}")
    String name;

    @GetMapping("/hello")
    public String hello(){
        return name;
    }
}
