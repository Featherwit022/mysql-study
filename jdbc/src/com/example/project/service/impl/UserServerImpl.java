package com.example.project.service.impl;
import com.example.project.dao.UserDao;
import com.example.project.dao.impl.UserDaoImpl;
import com.example.project.entity.User;
import com.example.project.service.UserServer;

public class UserServerImpl implements UserServer {
    private final UserDao userDao=new UserDaoImpl();
    @Override
    public String login(String username, String password) {
        User user= userDao.getUserByUsername(username);
        if(user!=null && password.equals(user.getPassword())){
            return "登录成功";
        }else{
            return "账号或密码错误";
        }
    }

    @Override
    public String register(String username, String password) {
        User user= userDao.getUserByUsername(username);
        if(user!=null){
            return "用户名已被使用";
        }
        int result =userDao.saveUser(username,password);
        return result ==1?"注册成功":"注册失败";
    }
}
