package com.jiangfeixiang.springsecurity.config;

import com.jiangfeixiang.springsecurity.dao.UserRepository;
import com.jiangfeixiang.springsecurity.entity.UserDO;
import com.jiangfeixiang.springsecurity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * @title
 * @description  自定义的用户存储认证
 * @author jiangfeixiang
 * @updateTime
 * @throws
 */
@Slf4j
@Component
public class DbUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 注入加密bean
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 校验用户
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录的用户名：" + username);
        UserDO userDO = userRepository.findByUsername(username);
        System.out.println(userDO);
        if (userDO == null){
            throw new UsernameNotFoundException("用户不存在！");
        }
        //密码加密
        String password =  passwordEncoder.encode(userDO.getPassword());
        log.info("登录的密码：" + password);
        //返回：用户名，密码(加密)，角色
        return new User(userDO.getUsername(),password, AuthorityUtils.commaSeparatedStringToAuthorityList(userDO.getRoles()));
    }

}