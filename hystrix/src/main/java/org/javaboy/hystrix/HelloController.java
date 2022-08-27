package org.javaboy.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.javaboy.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author szh
 * @Date 2022/5/28 18:53
 * @PackageName:org.javaboy.hystrix
 * @ClassName: HelloController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello(){
        return helloService.hello();
    }

    @GetMapping("/hello2")
    public void hello2(){
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        /*两种方式 启动  只能选择一种 new出来的HelloCommand只能执行一次*/
        HelloCommand helloCommand = new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("javaboy")), restTemplate,"javaboy");
        //helloCommand.run() //不能这样写

        String execute = helloCommand.execute(); //直接执行
        System.out.println(execute);

        HelloCommand helloCommand2 = new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("javaboy")), restTemplate,"javaboy");
        try {
            Future<String> queue = helloCommand2.queue();
            String s = queue.get();
            System.out.println(s);  //先入队，后执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ctx.close();
    }

    @GetMapping("/hello3")
    public void hello3(){
        Future<String> stringFuture = helloService.hello2();
        String s = null;
        try {
            s = stringFuture.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/hello4")
    public void hello4(){
        /*初始化HystrixRequestContext*/
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        /*第一次请求完，数据已经缓存完了*/
        String javaboy = helloService.hello3("javaboy");
        /*删除数据，同时缓存中的数据已经被删除*/
        /*第二次请求时，虽然参数还是JavaBoy，但是缓存数据已经没了，所以这一次，provider还是辉收到请求*/
        helloService.deleteUserByName("javaboy");
        javaboy = helloService.hello3("javaboy");
        /*close后，缓存失效*/
        ctx.close();
    }

    @Autowired
    UserService userService;

    @GetMapping("/hello5")
    public void hello5() throws ExecutionException, InterruptedException {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        UserCollapseCommand command1 = new UserCollapseCommand(userService, 99);
        UserCollapseCommand command2 = new UserCollapseCommand(userService, 98);
        UserCollapseCommand command3 = new UserCollapseCommand(userService, 97);
        UserCollapseCommand command4 = new UserCollapseCommand(userService, 96);
        Future<User> q1 = command1.queue();
        Future<User> q2 = command2.queue();
        Future<User> q3 = command3.queue();
        Future<User> q4 = command4.queue();
        User u1 = q1.get();
        User u2 = q2.get();
        User u3 = q3.get();
        User u4 = q4.get();
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
        System.out.println(u4);
        ctx.close();
    }

    @GetMapping("/hello6")
    public void hello6() throws ExecutionException, InterruptedException {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        Future<User> q1 = userService.getUserById(99);
        Future<User> q2 = userService.getUserById(98);
        Future<User> q3 = userService.getUserById(97);
        Future<User> q4 = userService.getUserById(96);
        User u1 = q1.get();
        User u2 = q2.get();
        User u3 = q3.get();
        User u4 = q4.get();
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
        System.out.println(u4);
        ctx.close();
    }


}
