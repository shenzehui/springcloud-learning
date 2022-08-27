package org.javboy.resilience4j2;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author szh
 * @Date 2022/5/31 19:05
 * @PackageName:org.javboy.resilience4j2
 * @ClassName: HelloService
 * @Description: TODO
 * @Version 1.0
 */
@Service
//@Retry(name = "retryA")  //表示要使用的重试策略
@CircuitBreaker(name = "cbA",fallbackMethod = "error")
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    /*模拟RateLimiter请求*/
    public String hello(){
        for (int i = 0; i < 5; i++) {
            restTemplate.getForObject("http://localhost:1113/hello", String.class);
        }
        return "success";
    }

    /*这里的error方法必须加异常参数*/
    public String error(Throwable t){
        System.out.println(t.getMessage());
        return "error";
    }
}
