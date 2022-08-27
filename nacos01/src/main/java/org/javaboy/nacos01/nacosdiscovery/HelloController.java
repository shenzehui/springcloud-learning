package org.javaboy.nacos01.nacosdiscovery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author szh
 * @Date 2022/7/11 20:26
 * @PackageName:org.javaboy.nacos01.nacosdiscovery
 * @ClassName: HelloController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
public class HelloController {

    @Value("${server.port}")
    Integer port;

    @GetMapping("/hello")
    public String hello() {
        return "hello" + port;
    }
}
