package org.javaboy.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @Author szh
 * @Date 2022/7/10 20:25
 * @PackageName:org.javaboy.stream
 * @ClassName: MyChannel
 * @Description: 自定义消息通道
 * @Version 1.0
 */
public interface MyChannel {
    /*输入消息通道*/
    String INPUT = "javaboy-input";
    /*输出消息通道*/
    String OUTPUT = "javaboy-output";

    @Output(OUTPUT)
    MessageChannel output();

    @Input(INPUT)
    MessageChannel input();

}
