package org.javaboy.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @Author szh
 * @Date 2022/7/10 20:06
 * @PackageName:org.javaboy.stream
 * @ClassName: MsgReceiver
 * @Description: TODO
 * @Version 1.0
 */
// @EnableBinding表示绑定Sink 消息通道
@EnableBinding(Sink.class)
public class MsgReceiver {
    private static Logger logger = LoggerFactory.getLogger(MsgReceiver.class);

    @StreamListener(Sink.INPUT)
    public void receive(Object payload) {
        logger.info("Received:" + payload);
    }
}
