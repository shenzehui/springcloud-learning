package org.javaboy.consulconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author szh
 * @Date 2022/5/28 17:18
 * @PackageName:org.javaboy.consulconsumer
 * @ClassName: HelloController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
public class HelloController {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public void hello(){
        /*相当于eureka中的DiscoveryClient*/
        ServiceInstance choose = loadBalancerClient.choose("consul-provider");
        System.out.println("服务地址："+choose.getUri());
        System.out.println("服务名称："+choose.getServiceId());
        String s = restTemplate.getForObject(choose.getUri()+"/hello", String.class);
        System.out.println(s);
    }

}
