import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JdbcTest {
        public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String url="jdbc:mysql://localhost:3306/study?serverTimeZone=Asia/Shanghai";
        String user="root";
        String password="123456";
        List<Score> scores=new ArrayList<>();
        int limitScore=sc.nextInt();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cnn= DriverManager.getConnection(url,user,password);
            String sql="SELECT * FROM score WHERE score> ?";
            PreparedStatement s=cnn.prepareStatement(sql);
            s.setString(1, String.valueOf(limitScore));
            ResultSet rs=s.executeQuery();
            while(rs.next()){
                int id=rs.getInt(1);
                String name=rs.getString(2);
                String course=rs.getString(3);
                double score=rs.getDouble(4);
                scores.add(new Score(id,name,course,score));
            }
            rs.close();
            s.close();
            cnn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        scores.forEach(System.out::println);
    }
}
