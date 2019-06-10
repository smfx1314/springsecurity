package com.jiangfeixiang.springsecurity.service.impl;

import com.jiangfeixiang.springsecurity.dao.UserRepository;
import com.jiangfeixiang.springsecurity.entity.UserDO;
import com.jiangfeixiang.springsecurity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @title
 * @description
 * @author jiangfeixiang
 * @updateTime
 * @throws
 */
@Service
@Primary
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 加密
     * @param userDO
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 添加用户
     * @param userDO
     */
    @Override
    public void insert(UserDO userDO) {
        String username = userDO.getUsername();
        if (exist(username)){
            throw new RuntimeException("用户名已存在！");
        }
        userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
        userRepository.save(userDO);
    }

    /**
     * 根据用户名查询
     * @param username 账号
     * @return
     */
    @Override
    public UserDO getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 判断用户是否存在
     */
    private boolean exist(String username){
        UserDO userDO = userRepository.findByUsername(username);
        return (userDO != null);
    }

}