package org.javaboy.zipkin01;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author szh
 * @Date 2022/7/11 15:40
 * @PackageName:org.javaboy.zipkin01
 * @ClassName: HelloController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
public class HelloController {

    private static final Log log = LogFactory.getLog(HelloController.class);

    @GetMapping("/hello")
    public String hello(String name){
        log.info("zipkin01-hello");
        return "hello" + name + "!";
    }

}
