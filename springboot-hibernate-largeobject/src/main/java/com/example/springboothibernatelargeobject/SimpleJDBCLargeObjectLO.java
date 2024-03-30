package com.example.springboothibernatelargeobject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.postgresql.largeobject.LargeObjectManager;

public class SimpleJDBCLargeObjectLO {
 
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
            stmt.execute("create table imagejdbc (imgname text, imgoid oid)");   
            
            // Insert an image
            // All LargeObject API calls must be within a transaction block
            conn.setAutoCommit(false);
            File inputFile = new File ("target/classes/myimage.png");
            try(FileInputStream fis = new FileInputStream(inputFile);
                PreparedStatement ps = conn.prepareStatement("insert into imagejdbc values (?,?)")){
                ps.setString(1, inputFile.getName());
                ps.setBlob(2, fis, (int) inputFile.length()); // beware of memory usage
                ps.executeUpdate();
                System.out.println("Image inserted into database");
            }
            // Finally, commit the transaction.
            conn.commit();
            conn.setAutoCommit(true);


            // Read the image (read as byte[] or use InputStream)
            // All LargeObject API calls must be within a transaction block
            conn.setAutoCommit(false);            
            File outputFile = new File ("target/classes/myimage-out.png");
            try(FileOutputStream fos = new FileOutputStream(outputFile);
                PreparedStatement ps = conn.prepareStatement("SELECT imgoid FROM imagejdbc WHERE imgname = ?")){
                ps.setString(1, "myimage.png");
                try(ResultSet rs = ps.executeQuery()){
                    rs.next();
                    // Demo purpose: Read data as InputSteam
                    Blob blob = rs.getBlob(1);
                    try (InputStream is = blob.getBinaryStream()) {
                        int img;
                        while ((img = is.read()) != -1)
                            fos.write(img);
                    }
                }
                System.out.println("Image written to " + outputFile.getAbsolutePath());
            }
            // Finally, commit the transaction.
            conn.commit();
            conn.setAutoCommit(true);


            // Delete the records, together with LargeObject
            try(PreparedStatement ps = conn.prepareStatement("DELETE FROM imagejdbc WHERE imgname = ? RETURNING imgoid")){
                ps.setString(1, "myimage.png");
                try(ResultSet rs = ps.executeQuery()){
                    rs.next();
                    long oid = rs.getInt(1);
                     LargeObjectManager lobj = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
                     lobj.delete(oid);   // same as lob.unlink(oid) 
                     System.out.println("Record deleted, together with LO oid: " + oid);
                }
               
            }          

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
