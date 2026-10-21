package com.example.project.dao.impl;

import com.example.project.dao.UserDao;
import com.example.project.entity.User;
import com.example.project.util.JdbcUtil;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User getUserByUsername(String username) {
        String sql="SELECT username,password FROM user WHERE username=?";
        List<User> users = JdbcUtil.query(sql, User.class, username);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public int saveUser(String username, String password) {
        String sql="INSERT INTO `user` (`username`, `password`) VALUES (?, ?)";
        return JdbcUtil.update(sql,username,password);
    }
}
