package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class AdvancedJDBCConnection {
 
    public static void main(String[] args) {

        String url="jdbc:postgresql://localhost/mytest";
        String dbuser="hr";
        String dbpass="hr";

        Properties props = new Properties();
        props.setProperty("user",dbuser);
        props.setProperty("password", dbpass);
        props.setProperty("ssl", "false");
        props.setProperty("ApplicationName", "JDBCConnection: PrepareThreshold1CacheModeForceCustom");
        props.setProperty("options", "-c plan_cache_mode=force_custom_plan -c auto_explain.log_analyze=1 -c auto_explain.log_min_duration=0");
        props.setProperty("prepareThreshold", "1");
        
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, props);
            System.out.println("Connected to the PostgreSQL server successfully.");
            
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE emp (id INT primary key, name TEXT, salary smallint)");   
            stmt.execute("INSERT INTO emp SELECT x,'Employee-'||x,((1+random())/2*10000)::smallint FROM generate_series(1,5) x");

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM emp WHERE id = ?");
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();           
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
