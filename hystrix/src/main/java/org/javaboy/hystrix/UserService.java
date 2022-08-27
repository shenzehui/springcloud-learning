package org.javaboy.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringUtils;
import org.javaboy.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @Author szh
 * @Date 2022/5/29 10:31
 * @PackageName:org.javaboy.hystrix
 * @ClassName: UserService
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand
    public List<User> getUserByIds(List<Integer> ids){
        User[] users = restTemplate.getForObject("http://provider/user/{1}", User[].class, StringUtils.join(ids, ","));
        return Arrays.asList(users);
    }

    /**
     * 批处理注解  batchMethod 指定批处理方法
     * @param id
     * @return
     */
    @HystrixCollapser(batchMethod = "getUserByIds",collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds",value = "200")})
    public Future<User> getUserById(Integer id){
        return null;
    }
}
