package org.javaboy.openfeign;

import org.javaboy.commons.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;

/**
 * @Author szh
 * @Date 2022/5/31 14:34
 * @PackageName:org.javaboy.openfeign
 * @ClassName: HelloServiceFallback
 * @Description: 服务降级的方法
 * @Version 1.0
 */
@Component
@RequestMapping("/javaboy")  //这里接口定义了两次，因此加一个请求前缀（自定义），防止地址接口重复
public class HelloServiceFallback implements HelloService {

    @Override
    public String hello() {
        return "error";
    }

    @Override
    public String hello2(String name) {
        return "error2";
    }

    @Override
    public User addUser2(User user) {
        return null;
    }

    @Override
    public void deleteUser2(Integer id) {

    }

    @Override
    public void getUserByName(String name) throws UnsupportedEncodingException {

    }
}
