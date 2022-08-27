package org.javaboy.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.Date;

/**
 * @Author szh
 * @Date 2022/7/10 20:31
 * @PackageName:org.javaboy.stream
 * @ClassName: MessageReceiver2
 * @Description: TODO
 * @Version 1.0
 */
@EnableBinding(MyChannel.class)
public class MessageReceiver2 {
    private Logger logger = LoggerFactory.getLogger(MessageReceiver2.class);

    @StreamListener(MyChannel.INPUT)
    public void receive(Object payload) {
        logger.info("Receive:" + payload + new Date());
    }
}
