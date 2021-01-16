package org.ds.health.dao;

import org.ds.health.pojo.User;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/13 19:58
 * @version: 1.0
 */
public interface UserDao {
    User findByUsername(String username);
}
