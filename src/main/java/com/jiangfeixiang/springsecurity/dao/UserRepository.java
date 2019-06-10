package com.jiangfeixiang.springsecurity.dao;

import com.jiangfeixiang.springsecurity.entity.UserDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDO, Long> {
    /**
     * 查询用户信息
     * @param username 账号
     * @return UserEntity
     */
    UserDO findByUsername(String username);

}