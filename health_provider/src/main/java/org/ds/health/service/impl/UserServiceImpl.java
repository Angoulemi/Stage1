package org.ds.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.ds.health.dao.UserDao;
import org.ds.health.pojo.User;
import org.ds.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
    * @description: 通过用户名查询用户角色权限
    * @author: Deshan
    * @date: 2021/1/13 19:58
    * @param: [username]
    * @return: org.ds.health.pojo.User
    */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
