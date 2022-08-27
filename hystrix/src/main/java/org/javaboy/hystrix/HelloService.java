package org.javaboy.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * @Author szh
 * @Date 2022/5/28 18:51
 * @PackageName:org.javaboy.hystrix
 * @ClassName: HelloService
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 在这个方法中，我们将发起一个远程调用，去调用 provider 中提供的 /hello 接口
     *
     * 但是，这个调用可能会失败。
     *
     * 我们在这个方法上添加@HystrixCommand 注解，配置fallbackMethod 属性，这个属性表示该方法调用失败时的临时替代方法
     * @return
     */
    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = ArithmeticException.class) //忽略异常
    public String hello(){
        int i = 1/0;
        return restTemplate.getForObject("http://provider/hello", String.class);
    }

    /*注解实现异步回调*/
    @HystrixCommand(fallbackMethod = "error")
    public Future<String> hello2(){
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForObject("http://provider/hello", String.class);
            }
        };
    }
    @HystrixCommand(fallbackMethod = "error2")
    @CacheResult //注解表示该方法的请求结果被缓存下来，默认情况下缓存的key就是方法的参数，缓存的 value 就是方法的返回值.这里跟spring Cache 的原理一样
    // 每次进来的key和缓存下来的key比较，是否有一样的，有，则直接获取，没有就缓存
    public String hello3(String name){
        return restTemplate.getForObject("http://provider/hello2?name={1}", String.class,name);
    }

    @HystrixCommand(fallbackMethod = "error2")
    @CacheRemove(commandKey = "hello3")  //commandKey 其实就是缓存方法的名字，目的是寻找缓存数据
    public String deleteUserByName(String name){
        //此处省略了数据库删除操作
        return null;
    }



    public String error2(String name){
        return "error：javaboy";
    }

    /**
     * 临时替代方法
     * 注意：这个方法名字和 fallbackMethod一致
     * 方法返回值也要和其保持一致
     * @return
     */
    public String error(Throwable t){
        return "error" + t.getMessage();
    }
}
