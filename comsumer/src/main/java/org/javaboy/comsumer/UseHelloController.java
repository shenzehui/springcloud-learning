package org.javaboy.comsumer;

import org.javaboy.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author szh
 * @Date 2022/5/28 10:34
 * @PackageName:org.javaboy.comsumer
 * @ClassName: UseHelloController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
public class UseHelloController {
    @Autowired
    DiscoveryClient discoveryClient;

    /*Java代码调用请求接口 未涉及到Eureka Server*/
    @GetMapping("/hello1")
    public String hello1(){
        HttpURLConnection con = null;
        try {
            URL url = new URL("http://localhost:1113/hello");
            con = (HttpURLConnection) url.openConnection();
            if(con.getResponseCode()==200){
                /*请求成功*/
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String s = br.readLine();
                br.close();
                return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    /*动态配置*/
    @GetMapping("/hello2")
    public String hello2(){
        /*根据服务名称获取实例 这里是provider实例*/
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
        ServiceInstance instance = list.get(0);
        /*获取地址和端口*/
        String host = instance.getHost();
        int port = instance.getPort();
        StringBuffer sb = new StringBuffer();
        /*拼接请求地址*/
        sb.append("http://")
                .append(host)
                .append(":")
                .append(port)
                .append("/hello");
        HttpURLConnection con = null;
        /*发送请求*/
        try {
            URL url = new URL(sb.toString());
            con = (HttpURLConnection) url.openConnection();
            if(con.getResponseCode()==200){
                /*请求成功*/
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String s = br.readLine();
                br.close();
                return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    /*线性 负载均衡*/
    int count = 0;
    @GetMapping("/hello3")
    public String hello3(){
        /*根据服务名称获取实例 这里是provider实例*/
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
        /*0-1-0-1-0-1*/
        ServiceInstance instance = list.get((count++)% list.size());
        /*获取地址和端口*/
        String host = instance.getHost();
        int port = instance.getPort();
        StringBuffer sb = new StringBuffer();
        sb.append("http://")
                .append(host)
                .append(":")
                .append(port)
                .append("/hello");
        HttpURLConnection con = null;
        try {
            URL url = new URL(sb.toString());
            con = (HttpURLConnection) url.openConnection();
            if(con.getResponseCode()==200){
                /*请求成功*/
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String s = br.readLine();
                br.close();
                return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @Autowired
    @Qualifier(value = "restTemplateOne")
    RestTemplate restTemplateOne;

    @GetMapping("/hello4")
    public String hello4(){
        /*根据服务名称获取实例 这里是provider实例*/
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
        /*0-1-0-1-0-1*/
        ServiceInstance instance = list.get((count++)% list.size());
        /*获取地址和段鸥*/
        String host = instance.getHost();
        int port = instance.getPort();
        StringBuffer sb = new StringBuffer();
        sb.append("http://")
                .append(host)
                .append(":")
                .append(port)
                .append("/hello");
        String s = restTemplateOne.getForObject(sb.toString(), String.class);
        return s;
    }

    /*负载均衡restTemplate*/
    @Autowired
    @Qualifier(value = "restTemplate")
    RestTemplate restTemplate;

    @GetMapping("/hello5")
    public String hello5(){
        /*具有负载均衡功能的RestTemplate 地址应该模糊化（provider）  */
        /*没有负载均衡功能的RestTemplate 地址应该要具体*/
        return restTemplate.getForObject("http://provider/hello", String.class);
    }

    /*RestTemplate getForObject 和 getForEntity*/
    @GetMapping("/hello6")
    public void hello6(){
        /*第三个参数是携带参数，占位符中的数据*/
        String result = restTemplate.getForObject("http://provider/hello2?name={1}", String.class, "javaboy");
        System.out.println("result = " + result);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://provider/hello2?name={1}", String.class, "javbaoy");
        String body = responseEntity.getBody();
        System.out.println("body = " + body);
        HttpStatus statusCode = responseEntity.getStatusCode();
        /*响应状态码 200 OK*/
        System.out.println("statusCode = " + statusCode);
        /*响应状态码 200*/
        int statusCodeValue = responseEntity.getStatusCodeValue();
        System.out.println("statusCodeValue = " + statusCodeValue);

        /*获取响应头*/
        HttpHeaders headers = responseEntity.getHeaders();
        Set<String> keySet = headers.keySet();
        for (String s : keySet) {
            System.out.println(s+":"+headers.get(s));
        }
    }

    /*RestTemplate中的GET请求*/
    @GetMapping("/hello7")
    public void hell07() throws UnsupportedEncodingException {
        /*使用占位符传参*/
        String rs1 = restTemplate.getForObject("http://provider/hello2?name={1}", String.class, "javaboy");
        System.out.println("rs1 = " + rs1);
        /*使用map传参*/
        Map<String,Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        String rs2 = restTemplate.getForObject("http://provider/hello2?name={name}", String.class, map);
        System.out.println("rs2 = " + rs2);
        /*传递中文参数*/
        String url = "http://provider/hello2?name="+ URLEncoder.encode("张三", "UTF-8");
        URI uri = URI.create(url);
        String rs3 = restTemplate.getForObject(uri, String.class);
        System.out.println("rs3 = " + rs3);
    }

    @GetMapping("/hello8")
    public void hello8(){
        /* key/value形式传参 */
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("username", "javaboy");
        map.add("password", "123");
        map.add("id", 99);
        User user = restTemplate.postForObject("http://provider/user1", map, User.class);
        System.out.println("user = " + user);

        /*以JSON形式传参*/
        user.setId(98);
        user = restTemplate.postForObject("http://provider/user2", user,User.class);
        System.out.println("user = " + user);
    }

    @GetMapping("/hello9")
    public void hello9(){
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("username", "javaboy");
        map.add("password", "123");
        map.add("id", 99);
        /*重定向URI 就是方法返回的值，要跳转的地址*/
        URI uri = restTemplate.postForLocation("http://provider/register", map);
        /*发起跳转地址请求  这里注意：这是一个有负载均衡的restTemplate，所以需要有provider 所以重定向的时候 路径应该补全*/
        String result = restTemplate.getForObject(uri, String.class);
        System.out.println("result = " + result);
    }

    @GetMapping("/hello10")
    public void hello10(){
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("username", "javaboy");
        map.add("password", "123");
        map.add("id", 99);
        /*重定向URI 就是方法返回的值，要跳转的地址*/
        restTemplate.put("http://provider/user1", map);
        User user = new User();
        user.setId(98);
        user.setUsername("zhangsan");
        user.setPassword("456");
        restTemplate.put("http://provider/user2", user);
    }

    @GetMapping("/hello11")
    public void hello11(){
        restTemplate.delete("http://provider/user1?id={1}", 99);
        restTemplate.delete("http://provider/user2/{1}", 99);
    }
}
