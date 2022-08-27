package org.javaboy.provider;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.javaboy.api.IUserService;
import org.javaboy.commons.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

/**
 * @Author szh
 * @Date 2022/5/28 10:27
 * @PackageName:org.javaboy.provider
 * @ClassName: HelloController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
public class HelloController implements IUserService {
    @Value("${server.port}")
    Integer port;

    @Override
    @RateLimiter(name = "rlA")  //属性name是properties中配置的限流名
    public String hello() {
//        System.out.println("hello java" + port);
        System.out.println(new Date());
//        int i = 1 / 0;
        return "hello javaboy：" + port;
    }

    @Override
    public String hello2(String name) {
        System.out.println(new Date() + ">>>" + name);
        return "hello " + name;
    }

    @PostMapping("/user1")
    public User addUser1(User user){
        return user;
    }

    @Override
    public User addUser2(@RequestBody User user){
        return user;
    }

    @PutMapping("/user1")
    public void updateUser1(User user){
        System.out.println(user);
    }

    @PutMapping("/user2")
    public void updateUser2(@RequestBody User user){
        System.out.println(user);
    }

    @DeleteMapping("/user1")
    public void deleteUser1(Integer id){
        System.out.println(id);
    }

    @Override
    public void deleteUser2(@PathVariable Integer id){
        System.out.println(id);
    }

    @Override
    /*请求头接收参数*/
    public void getUserByName(@RequestHeader String name) throws UnsupportedEncodingException {
        System.out.println(URLDecoder.decode(name, "UTF-8"));
    }
}
