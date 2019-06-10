package com.jiangfeixiang.springsecurity.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
/**
 * @title
 * @description
 * @author jiangfeixiang
 * @updateTime
 * @throws
 */
@Controller
public class UserController {

    @GetMapping("/user")
    public String user(){
        return "user/user";
    }
}
