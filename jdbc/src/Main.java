import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        String url="jdbc:mysql://localhost:3306/study?serverTimeZone=Asia/Shanghai";
        String dbUser="root";
        String dbPassword="123456";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Scanner sc=new Scanner(System.in);
        try (Connection cnn= DriverManager.getConnection(url,dbUser,dbPassword);){

            System.out.println("输入你的id");
            String inputId=sc.nextLine();
            System.out.println("输入你的密码");
            String inputPassword=sc.nextLine();
            String sql="SELECT * FROM user where id='"+inputId+"' AND password='"+inputPassword+"'";
            try(Statement st=cnn.createStatement();ResultSet rs=st.executeQuery(sql);){
                boolean flag=false;
                User user=new User();
                while(rs.next()){
                    flag=true;
                    user.setName(rs.getString("name"));
                    user.setId(rs.getString("id"));
                    user.setPassword(rs.getString("password"));
                    user.setStatus(rs.getInt("status"));
                }
                if(flag){
                    System.out.print("登录成功：");
                    if(user.getStatus()==1){
                        System.out.println("管理员"+user.getName()+"登录");
                    }else{
                        System.out.println("用户"+user.getName()+"登录");
                    }
                }else{
                    System.out.println("账号或密码错误");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
