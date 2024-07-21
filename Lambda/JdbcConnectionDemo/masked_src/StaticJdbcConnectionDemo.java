package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

// Handler value: example.Handler
public class StaticJdbcConnectionDemo implements RequestHandler<SQSEvent, Void>{

    String url="jdbc:postgresql://xxxxxxxxx.cinzaqerfom1.ap-southeast-1.rds.amazonaws.com:5432/postgres";
    String dbuser="postgres";
    String dbpass="xxxxxxx";
    Properties props = new Properties();

    static Connection conn = null;

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context)
    {
        LambdaLogger logger = context.getLogger();
        logger.log("EVENT TYPE: " + sqsEvent.getClass());

        //initialize connection only if it's NULL
        try {
            props.setProperty("user",dbuser);
            props.setProperty("password", dbpass);
            if (conn == null){
                conn = DriverManager.getConnection(url, props);
                logger.log("Connected to the PostgreSQL server successfully.");
            }
        }
        catch (SQLException e) {
            logger.log("Exception at connection open");
            logger.log(e.getMessage());
        }

            // Sleep for 5 seconds to avoid Lambda instance quickly being reused
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
                logger.log("Sleep interrupted");
            }

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select pg_backend_pid()");
                while (rs.next()) {
                    logger.log("DB server process ID is: "+String.valueOf(rs.getInt("pg_backend_pid")));
                }
                stmt.close();   
            }     
            catch (SQLException e) {
                logger.log("Exception at query processing");
                logger.log(e.getMessage());
            }

            return null;
    }

  static {
    Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
            System.out.println("[runtime] ShutdownHook triggered");

            System.out.println("[runtime] Cleaning up");
            // perform actual clean up work here.
            try {
                conn.close();
            }
            catch (SQLException e) {
               System.out.println("Exception at connection close");
               System.out.println(e.getMessage());
            }
            System.out.println("[runtime] exiting");
            System.exit(0);
        }
    });
}

}