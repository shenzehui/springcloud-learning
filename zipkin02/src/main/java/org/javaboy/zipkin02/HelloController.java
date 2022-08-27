package org.javaboy.zipkin02;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author szh
 * @Date 2022/7/11 15:45
 * @PackageName:org.javaboy.zipkin02
 * @ClassName: HelloController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
public class HelloController {
    @Autowired
    RestTemplate restTemplate;

    private static final Log log = LogFactory.getLog(HelloController.class);

    @GetMapping("/hello")
    public void hello() {
        String s = restTemplate.getForObject("http://localhost:8080/hello?name={1}", String.class, "javaboy");
        log.info(s);
    }
}
