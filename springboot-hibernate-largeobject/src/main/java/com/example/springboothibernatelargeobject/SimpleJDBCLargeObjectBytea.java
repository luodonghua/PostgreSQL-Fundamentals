package com.example.springboothibernatelargeobject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SimpleJDBCLargeObjectBytea {
 
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
            
            // Re-create table imagejdbc
            // Whole example is based on code snippets on https://jdbc.postgresql.org/documentation/binary-data/
            Statement stmt = conn.createStatement();
            stmt.execute("drop table if exists imagejdbc");  
            stmt.execute("create table imagejdbc (imgname text, img bytea)");   
            
            // Insert an image
            File inputFile = new File ("target/classes/myimage.png");
            try(FileInputStream fis = new FileInputStream(inputFile);
                PreparedStatement ps = conn.prepareStatement("insert into imagejdbc values (?,?)")){
                ps.setString(1, inputFile.getName());
                ps.setBinaryStream(2, fis, (int) inputFile.length()); // beware of memory usage
                ps.executeUpdate();
                System.out.println("Image inserted into database");
            }
            

            // Read the image (read as byte[] or use InputStream)
            File outputFile = new File ("target/classes/myimage-out.png");
            try(FileOutputStream fos = new FileOutputStream(outputFile);
                PreparedStatement ps = conn.prepareStatement("SELECT img FROM imagejdbc WHERE imgname = ?")){
                ps.setString(1, "myimage.png");
                try(ResultSet rs = ps.executeQuery()){
                    rs.next();
                    // Demo purpose: Read data as  byte[]
                    byte[] img = rs.getBytes(1); // beware of memory usage
                    fos.write(img);
                }
                System.out.println("Image written to " + outputFile.getAbsolutePath());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
