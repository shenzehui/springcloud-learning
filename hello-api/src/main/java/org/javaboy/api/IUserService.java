package org.javaboy.api;

import org.javaboy.commons.User;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * @Author szh
 * @Date 2022/5/31 14:08
 * @PackageName:org.javaboy.api
 * @ClassName: IUserService
 * @Description: TODO
 * @Version 1.0
 */
public interface IUserService {
    @GetMapping("/hello")  //相当于getForObject(url,...)
    String hello(); //这里的方法名无所谓，随意取 String 是provider的返回地址

    @GetMapping("/hello2")
   /*这边必须要有这个注解，且指定出参数名（注意：参数名一样也要指定！！！）*/
    String hello2(@RequestParam("name") String name);

    @PostMapping("/user2")
    /*json就这样写即可*/
    User addUser2(@RequestBody User user);

    @DeleteMapping("/user2/{id}")
    void deleteUser2(@PathVariable("id") Integer id);

    @GetMapping("/user3")
    void getUserByName(@RequestHeader("name") String name) throws UnsupportedEncodingException;

}
