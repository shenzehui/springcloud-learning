package org.javaboy.sentinel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author szh
 * @Date 2022/7/11 22:23
 * @PackageName:org.javaboy.sentinel
 * @ClassName: HelloController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello sentinel";
    }
}
