package com.jiangfeixiang.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @title
 * @description
 * @author jiangfeixiang
 * @updateTime
 * @throws
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    /**
     * 注入 dbUserDetailsService
     */
    @Autowired
    private DbUserDetailsService dbUserDetailsService;

    /**
     * 创建PasswordEncoder Bean
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * 匹配 "/" 路径，不需要权限即可访问
     * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
     * 登录地址为 "/login"，登录成功默认跳转到页面 "/user"
     * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
     * 默认启用 CSRF
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //任何人都可以访问
                .antMatchers("/").permitAll()
                //持有USER权限的用户可以访问
                .antMatchers("/user").hasRole("USER")
                .and()
                //自定义登录：loginPage是登录成功页面地址。accessDeniePage登录失败跳转地址。
                .formLogin().loginPage("/login").defaultSuccessUrl("/user")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
                //.and()
                //强制安全性通道，只要是对“/admin/info”的请求，spring security都认为需要安全性通道，并自动将请求重定向到https上。
                //.requiresChannel().antMatchers("/admin/info").requiresSecure();
    }

    /**
     * 用户存储认证方式---基于内存的用户存储
     * 在内存中设置了两个用户admin和user同时设置对应密码和所拥有的权限。
     * roles()方法是authorities()方法的简写形式。
     * roles()方法所给定的值都会加一个”ROLE_”前缀，并将其作为权限授予用户
     */
   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //基于内存的用户存储、认证
        auth.inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN","USER")
                .and()
                .withUser("user").password("user").roles("USER");
    }*/


    /**
     * 自定义的用户存储认证
     * 只需要提供一个UserDetailService接口实现即可。
     * @param auth
     * @throws Exception
     */
   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //基于数据库的用户存储、认证
    auth.userDetailsService(dbUserDetailsService);
    }

}
