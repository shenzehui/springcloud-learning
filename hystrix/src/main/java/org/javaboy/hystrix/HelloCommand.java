package org.javaboy.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @Author szh
 * @Date 2022/5/28 20:01
 * @PackageName:org.javaboy.hystrix
 * @ClassName: HelloCommand
 * @Description: TODO
 * @Version 1.0
 */
/*这里泛型就是访问的返回结果*/
public class HelloCommand extends HystrixCommand<String> {

    @Autowired
    RestTemplate restTemplate;

    String name;

    public HelloCommand(Setter setter, RestTemplate restTemplate,String name) {
        super(setter);
        this.name = name;
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        /*发起请求*/
//        int i = 1 / 0;
        return restTemplate.getForObject("http://provider/hello2?name={1}", String.class,name);
    }

    /*指定缓存中的key*/
    @Override
    protected String getCacheKey() {
        return name;
    }

    /**
     * 这个方法就是请求失败的回调  与注解实现中的error方法对应
     * @return
     */
    @Override
    protected String getFallback() {
        /*打印异常信息*/
        return "error-extends：" + getExecutionException().getMessage();
    }
}
