package com.example.project.util;

import com.example.project.entity.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUtil {
    private static final String url="jdbc:mysql://localhost:3306/study?serverTimeZone=Asia/Shanghai";
    private static final String dbUser="root";
    private static final String dbPassword="123456";
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static int update(String sql, Object...params){
        int result=0;
        Connection cnn= null;
        PreparedStatement st= null;
        try {
            cnn= DriverManager.getConnection(url,dbUser,dbPassword);
            st=cnn.prepareStatement(sql);
            if(params!=null){
                for(int i=0;i<params.length;++i){
                    st.setObject(i+1,params[i]);
                }
            }
            result=st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(st,cnn);
        }
        return result;
    }
    public static<T> List<T> query(String sql,Class<T> clazz,Object... params){
        List<T> dataList = new ArrayList<>();
        Connection cnn= null;
        PreparedStatement st= null;
        ResultSet rs = null;
        try{
            cnn= DriverManager.getConnection(url,dbUser,dbPassword);
            st=cnn.prepareStatement(sql);
            if(params!=null){
                for(int i=0;i<params.length;++i){
                    st.setObject(i+1,params[i]);
                }
            }
            rs=st.executeQuery();
            while(rs.next()){
                T t=createInstance(clazz,rs);
                dataList.add(t);
            }
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs,st,cnn);
        }
        return dataList;
    }

    private static <T> T createInstance(Class<T> clazz, ResultSet rs) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        T t=clazz.getConstructor().newInstance();
        ResultSetMetaData meta=rs.getMetaData();
        for(int i=1;i<=meta.getColumnCount();++i){
            String fieldName=meta.getColumnLabel(i);
            try {
                Field field=clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                String setter="set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                Method m=clazz.getMethod(setter, field.getType());
                m.invoke(t,rs.getObject(i,field.getType()));
            } catch (NoSuchFieldException | NoSuchMethodException ignored) {
            }
        }
        return t;
    }
    private static void close(AutoCloseable... closeables){
        if(closeables != null){
            for(AutoCloseable ac: closeables){
                if(ac != null){
                    try {
                        ac.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}
