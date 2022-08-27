package org.javaboy.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.soap.SAAJResult;

/**
 * @Author szh
 * @Date 2022/7/9 14:31
 * @PackageName:org.javaboy.configclient
 * @ClassName: HelloController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
@RefreshScope
public class HelloController {
    @Value("${javaboy}")
    String javaboy;

    @GetMapping("/hello")
    public String hello(){
        return javaboy;
    }
}
