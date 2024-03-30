package com.example.springboothibernateprocedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;

public class SimpleJDBCProcedure {
 
    public static void main(String[] args) {

        String url="jdbc:postgresql://localhost/mytest";
        String dbuser="hr";
        String dbpass="hr";

        Properties props = new Properties();
        props.setProperty("user",dbuser);
        props.setProperty("password", dbpass);
        // Required as special property setting as no return for procedure
        props.setProperty("escapeSyntaxCallMode", "callIfNoReturn");
        
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, props);
            System.out.println("Connected to the PostgreSQL server successfully.");
            
            Statement stmt = conn.createStatement();
            stmt.execute("truncate table employee");   
            
            // Procedure call without prepare statement
            stmt.execute("call add_new_employee('Employee-1',3000)");   

            // Procedure call with prepare statement
            CallableStatement AddNewEmployeeStmt = conn.prepareCall("{call add_new_employee(?,?) }");
            AddNewEmployeeStmt.setString(1, "Employee-2");
            AddNewEmployeeStmt.setInt(2, 4000);
            AddNewEmployeeStmt.execute();

            // Function call
            CallableStatement getSalaryByNameStmt = conn.prepareCall("{? = call get_salary_by_name(?) }");
            getSalaryByNameStmt.registerOutParameter(1, Types.INTEGER);
            getSalaryByNameStmt.setString(2, "Employee-1");
            getSalaryByNameStmt.execute();
            int salary = getSalaryByNameStmt.getInt(1);
            System.out.println("Employee-1 salary is " + salary);


            ResultSet rs = stmt.executeQuery("SELECT id, name, salary FROM employee ORDER BY id");
            while (rs.next()) {
                System.out.format("| %-2d | %-8s | %6d |%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("salary"));
            }

            stmt.close();
            AddNewEmployeeStmt.close();
            getSalaryByNameStmt.close();        
            conn.close();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
