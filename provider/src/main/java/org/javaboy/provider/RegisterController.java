package org.javaboy.provider;

import org.javaboy.commons.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author szh
 * @Date 2022/5/28 15:29
 * @PackageName:org.javaboy.provider
 * @ClassName: RegisterController
 * @Description: TODO
 * @Version 1.0
 */
@Controller
public class RegisterController {
    @PostMapping("/register")
    public String register(User user){
        return "redirect:http://provider/loginPage?username="+ user.getUsername();
    }

    @GetMapping("/loginPage")
    @ResponseBody
    public String loginPage(String username){
        return "loginPage:" + username;
    }

}
