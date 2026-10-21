package com.example.project.start;

import com.example.project.controller.UserController;

import java.sql.*;
import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        UserController user=new UserController();
        System.out.println("输入你的用户名");
        String username=sc.nextLine();
        System.out.println("输入你的密码");
        String password=sc.nextLine();
        String result=user.register(username,password);
        System.out.println(result);
    }
}
