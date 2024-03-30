import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SimpleJDBCConnection {
 
    public static void main(String[] args) {

        String url="jdbc:postgresql://localhost/mytest";
        String dbuser="hr";
        String dbpass="hr";

        Properties props = new Properties();
        props.setProperty("user",dbuser);
        props.setProperty("password", dbpass);
        
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, props);
            System.out.println("Connected to the PostgreSQL server successfully.");
            
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE emp (id INT primary key, name TEXT, salary smallint)");   
            stmt.execute("INSERT INTO emp SELECT x,'Employee-'||x,((1+random())/2*10000)::smallint FROM generate_series(1,5) x");

            ResultSet rs = stmt.executeQuery("SELECT id, name, salary FROM emp ORDER BY id");
            while (rs.next()) {
                System.out.format("| %-2d | %-8s | %6d |%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("salary"));
            }
            stmt.execute("DROP TABLE emp");
            stmt.close();
            conn.close();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
