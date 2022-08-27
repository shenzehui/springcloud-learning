package org.javaboy.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import org.junit.Test;

import java.time.Duration;
import java.util.Date;

/**
 * @Author szh
 * @Date 2022/5/31 15:51
 * @PackageName:org.javaboy.resilience4j
 * @ClassName: Resilience4jTest
 * @Description: TODO
 * @Version 1.0
 */
public class Resilience4jTest {
    @Test
    public void test1() {
        /*获取一个CircuitBreakerRegistry实例，可以调用ofDefault获取一个CircuitBreakerRegistry实例，也可以自定义属性*/
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                /*故障率阈值百分比，超过这个阈值，断路器就会打开*/
                .failureRateThreshold(50) //50%
                /*断路器保持打开时间，在到达的设置的时间之后，断路器会进入到 half open 状态 （半打开）*/
                .waitDurationInOpenState(Duration.ofMillis(1000))
                /*当断路器处于 half open 状态时，环形缓冲区的大小*/
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry r1 = CircuitBreakerRegistry.of(config);
        CircuitBreaker cb1 = r1.circuitBreaker("javaboy");
        CircuitBreaker cb2 = r1.circuitBreaker("javaboy", config); //第二种创建方式
        /*supplier 参数 是自定义函数*/
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb1, () -> "hello resilience4j");
        /*java8特性*/
        Try<String> result = Try.of(supplier)
                /*这里的v是前面函数的返回值*/
                .map(v -> v + " hello world");
        System.out.println(result.isSuccess());
        System.out.println(result.get());
    }

    @Test
    public void test2() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                /*两条数据的时候就会测试故障*/
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry r1 = CircuitBreakerRegistry.of(config);
        CircuitBreaker cb1 = r1.circuitBreaker("javaboy");
        System.out.println(cb1.getState());  //获取断路器的状态 ：CLOSE
        /*当故障率超过50%就会打开*/
        cb1.onError(0, new RuntimeException());
        System.out.println(cb1.getState());
        cb1.onError(0, new RuntimeException());
        System.out.println(cb1.getState());

//        cb1.reset();  //重置断路器
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb1, () -> "hello resilience4j");
        Try<String> result = Try.of(supplier)
                /*这里的v是前面函数的返回值*/
                .map(v -> v + " hello world");
        System.out.println(result.isSuccess());
        System.out.println(result.get());
    }

    @Test
    public void test3() {
        /*限流配置*/
        RateLimiterConfig config = RateLimiterConfig.custom()
                /*阈值刷新时间*/
                .limitRefreshPeriod(Duration.ofMillis(1000))
                .limitForPeriod(2) //阈值刷新的频次
                .timeoutDuration(Duration.ofMinutes(1000)) //限流之后的冷却时间
                .build();
        RateLimiter rateLimiter = RateLimiter.of("javaboy", config);
        CheckedRunnable checkedRunnable = RateLimiter.decorateCheckedRunnable(rateLimiter, () -> {
            System.out.println(new Date());
        });
        /*这里每秒处理2个请求*/
        Try.run(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .onFailure(t -> System.out.println(t.getMessage()));
    }

    /*请求重试测试*/
    @Test
    public void test4() {
        RetryConfig config = RetryConfig.custom()
                /*配置重试次数*/
                .maxAttempts(5)
                /*重试间隔时间*/
                .waitDuration(Duration.ofSeconds(1))  //ofMillis 毫秒数    ofSeconds 秒数
                /*执行过程中指定抛出异常，重试*/
                .retryExceptions(RuntimeException.class)
                .build();
        Retry retry = Retry.of("javaboy", config);
        Retry.decorateRunnable(retry, new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count++ < 3) {
                    System.out.println(count);
                    throw new RuntimeException();
                }
            }
        }).run();
    }
}
