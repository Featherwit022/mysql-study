package com.example.project.controller;

import com.example.project.service.UserServer;
import com.example.project.service.impl.UserServerImpl;

/**
 *控制层
 */
public class UserController {
    private final UserServer userServer=new UserServerImpl();
    public String login(String username,String password){
        return userServer.login(username,password);
    }
    public String register(String username,String password){
        return userServer.register(username,password);
    }
}
