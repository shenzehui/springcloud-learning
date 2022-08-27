package org.javaboy.sleuth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Author szh
 * @Date 2022/7/11 14:24
 * @PackageName:org.javaboy.sleuth
 * @ClassName: HelloService
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class HelloService {

    private static final Log LOG = LogFactory.getLog(HelloController.class);

    @Async //异步
    public String backgroundFun(){
        LOG.info("backgroundFun");
        return "backgroundFun";
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void sche1(){
        LOG.info("start:");
        backgroundFun();
        LOG.info("end:");
    }

}
