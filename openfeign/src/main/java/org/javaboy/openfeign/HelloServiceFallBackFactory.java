package org.javaboy.openfeign;

import feign.hystrix.FallbackFactory;
import org.javaboy.commons.User;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @Author szh
 * @Date 2022/5/31 14:45
 * @PackageName:org.javaboy.openfeign
 * @ClassName: HelloServiceFallBackFactory
 * @Description: TODO
 * @Version 1.0
 */
/*第二种方法，自定义FallbackFactory  这里的泛型是openfeign中的接口*/
@Component
public class HelloServiceFallBackFactory implements FallbackFactory<HelloService> {
    @Override
    public HelloService create(Throwable throwable) {
        return new HelloService() {
            @Override
            public String hello() {
                return "error---";
            }

            @Override
            public String hello2(String name) {
                return "error2---";
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
        };
    }
}
