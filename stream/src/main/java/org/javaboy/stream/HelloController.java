package org.javaboy.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author szh
 * @Date 2022/7/10 20:34
 * @PackageName:org.javaboy.stream
 * @ClassName: HelloController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
public class HelloController {
    private Logger logger = LoggerFactory.getLogger(MessageReceiver2.class);

    @Autowired
    MyChannel myChannel;

    @GetMapping("/hello")
    public void hello(){
        logger.info("send msg:" + new Date());
        //在header中设置延迟消息为3秒
        myChannel.output().send(MessageBuilder.withPayload("hello spring cloud stream!").setHeader("x-delay", 5000).build());
    }
}
