import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/study?serverTimeZone=Asia/Shanghai";
        String userName="root";
        String password1="123456";
        Scanner sc=new Scanner(System.in);
        List<User> users=new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cnn= DriverManager.getConnection(url,userName,password1);
            Statement st=cnn.createStatement();
            String sql="SELECT * FROM user";
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                String id=rs.getString("id");
                String name=rs.getString("name");
                String password=rs.getString("password");
                int status=rs.getInt("status");
                User temp=new User(id,name,password,status);
                users.add(temp);
            }
            User user_=new User();
            System.out.println("输入你的id");
            user_.setId(sc.next());
            System.out.println("输入你的密码");
            user_.setPassword(sc.next());
            for(User user:users){
                if(user_.equals(user)){
                    System.out.println("登录成功");
                    user_.setName(user.getName());
                    user_.setStatus(user.getStatus());
                    break;
                }
            }
            if(user_.getStatus()==0){
                System.out.println("登录失败，账号或用户名错误");
            }else if(user_.getStatus()==1){
                System.out.println("管理员"+user_.getName()+"登录");
            }else{
                System.out.println("用户"+user_.getName()+"登录");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
