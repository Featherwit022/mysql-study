package com.example.project.dao;

import com.example.project.entity.User;

/**
 * 持久层
 * */
public interface UserDao {
    User getUserByUsername(String username);

    int saveUser(String username, String password);
}
