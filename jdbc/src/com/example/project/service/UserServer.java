package com.example.project.service;
/**
 * 业务逻辑层
 * */
public interface UserServer {
    public String login(String username,String password);
    public String register(String username,String password);
}
