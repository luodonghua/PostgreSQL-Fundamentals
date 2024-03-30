import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class HikariCPConnectionPoolProps {
 
    private static HikariDataSource CreateHikariCPDataSource() {
        HikariConfig config = new HikariConfig("/HikariCPConnectionPoolProps.properties");
        return new HikariDataSource(config);
    }
    public static void main(String[] args) {

        try {
            Connection conn = CreateHikariCPDataSource().getConnection();
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
