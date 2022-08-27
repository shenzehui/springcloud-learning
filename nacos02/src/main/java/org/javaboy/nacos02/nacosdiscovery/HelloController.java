package org.javaboy.nacos02.nacosdiscovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author szh
 * @Date 2022/7/11 20:34
 * @PackageName:org.javaboy.nacos02.nacosdiscovery
 * @ClassName: HelloController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
public class HelloController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello(){
        return restTemplate.getForObject("http://nacos01:8080/hello", String.class);
    }

}
