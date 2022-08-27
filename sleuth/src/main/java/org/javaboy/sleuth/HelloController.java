package org.javaboy.sleuth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author szh
 * @Date 2022/7/11 14:09
 * @PackageName:org.javaboy.sleuth
 * @ClassName: HelloController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
public class HelloController {

    private static final Log LOG = LogFactory.getLog(HelloController.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        LOG.info("hello Spring Cloud Sleuth!");
        return "hello Spring Cloud Sleuth!";
    }

    @GetMapping("/hello2")
    public String hello2() {
        LOG.info("hello2");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return restTemplate.getForObject("http://localhost:8080/hello3", String.class);
    }

    @GetMapping("/hello3")
    public String hello3() {
        LOG.info("hello3");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello3";
    }

    @GetMapping("/hello4")
    public String hello4(){
        LOG.info("hello4");
        return helloService.backgroundFun();
    }
}
