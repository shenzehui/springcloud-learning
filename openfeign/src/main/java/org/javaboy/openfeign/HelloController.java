package org.javaboy.openfeign;

import org.javaboy.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author szh
 * @Date 2022/5/29 16:38
 * @PackageName:org.javaboy.openfeign
 * @ClassName: HelloController
 * @Description: 暴露接口
 * @Version 1.0
 */
@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String hello() throws UnsupportedEncodingException {
        String s = helloService.hello2("szh");
        System.out.println(s);

        User user = new User();
        user.setId(1);
        user.setUsername("javaboy");
        user.setPassword("123456");
        User u = helloService.addUser2(user);
        System.out.println(u);

        helloService.deleteUser2(1);

        /*这里需要转码*/
        helloService.getUserByName(URLEncoder.encode("江南的一点", "UTF-8"));
        return helloService.hello();
    }
}
