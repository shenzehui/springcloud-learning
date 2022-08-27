package org.javaboy.provider;

import org.javaboy.commons.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author szh
 * @Date 2022/5/29 10:26
 * @PackageName:org.javaboy.provider
 * @ClassName: UserController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
public class UserController {
    @GetMapping("/user/{ids}") //假设consumer 传过来的多个id 的格式是 1,2,3,4,5....
    public List<User> getUserByIds(@PathVariable String  ids){
        System.out.println(ids);
        String[] split = ids.split(",");
        List<User> users = new ArrayList<>();
        for (String s : split) {
            User user = new User();
            user.setId(Integer.parseInt(s));
            users.add(user);
        }
        return users;
    }
}
