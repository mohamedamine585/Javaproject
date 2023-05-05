package Backend;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conn {


   public Connection Connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/java?characterEncoding=utf8","root","kiluathunder123@456");
        }
        catch(Exception e )
        {
            System.out.println(e);
        }
return  null;
    };
}
