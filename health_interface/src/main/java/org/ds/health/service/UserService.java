package org.ds.health.service;

import org.ds.health.pojo.User;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/13 19:57
 * @version: 1.0
 */
public interface UserService {
    User findByUsername(String username);
}
