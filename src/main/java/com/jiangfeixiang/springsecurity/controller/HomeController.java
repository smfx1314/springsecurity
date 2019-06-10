package com.jiangfeixiang.springsecurity.controller;

import com.jiangfeixiang.springsecurity.entity.UserDO;
import com.jiangfeixiang.springsecurity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
/**
 * @title
 * @description
 * @author jiangfeixiang
 * @updateTime
 * @throws
 */
@Controller
@Slf4j
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping({"/", "/index", "/home"})
    public String root(){
        log.info("111111111111111111");
        return "index";
    }

    @GetMapping("/loginPage")
    public String login(){
        log.info("222222222222222222");
        return "loginPage";
    }
    @GetMapping("/registerPage")
    public String register(){

        return "registerPage";
    }

    @PostMapping("/register")
    public String doRegister(UserDO userDO){
        // 此处省略校验逻辑
        userService.insert(userDO);
        return "redirect:user/user";
    }
}
