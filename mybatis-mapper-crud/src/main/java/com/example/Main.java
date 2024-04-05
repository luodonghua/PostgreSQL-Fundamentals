package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
            new SqlSessionFactoryBuilder().build(inputStream);
         SqlSession session = sqlSessionFactory.openSession();   

        // Truncate table to start new demo
        TruncateEmployeeTable(session); 
        // CREATE
        CreateEmployeeRecord(session);
        // READ
        ReadEmployeeRecord(session);
        // UPDATE
        UpdateEmployeeRecord(session);
        ReadEmployeeRecord(session);
        // DELETE
        DeleteEmployeeRecord(session);
        ReadEmployeeRecord(session);

        session.close();
    }


    private static void TruncateEmployeeTable(SqlSession session) {

        session.update("com.example.EmployeeMapper.truncateTable");
        session.commit();
        System.out.println("Records truncated successfully");
    }

    private static void CreateEmployeeRecord(SqlSession session) {

        // record 1
        Employee Employee1 = new Employee();
        Employee1.setName("Employee-1");
        Employee1.setSalary(3000);
        session.insert("com.example.EmployeeMapper.insert",Employee1);
        session.commit();
        System.out.println("Record created successfully: " + Employee1);

        // record 2
        Employee Employee2 = new Employee();
        Employee2.setName("Employee-2");
        Employee2.setSalary(4000);
        session.insert("com.example.EmployeeMapper.insert",Employee2);
        session.commit();
        System.out.println("Record created successfully: " + Employee2);
    }

    private static void ReadEmployeeRecord(SqlSession session) {
        List<Employee>  Employees= session.selectList("com.example.EmployeeMapper.getAll");
        System.out.println("Reading employee records:");
        for (Employee EmployeeObject : Employees) {
            System.out.format("| %-2d | %-8s | %6d |%n",
                        EmployeeObject.getID(),
                        EmployeeObject.getName(),
                        EmployeeObject.getSalary());
        }
    }

    private static void UpdateEmployeeRecord(SqlSession session) {

        int id = session.selectOne("com.example.EmployeeMapper.getMaxID");
        Employee Employee1 = session.selectOne("com.example.EmployeeMapper.getByID", id);
        Employee1.setSalary(6000);
        session.update("com.example.EmployeeMapper.update", Employee1);
        session.commit();
        System.out.println("Record updated successfully: " + Employee1);

    }

    private static void DeleteEmployeeRecord(SqlSession session) {

        int id = session.selectOne("com.example.EmployeeMapper.getMinID");
        session.delete("com.example.EmployeeMapper.deleteByID", id);
        session.commit();
        System.out.println("Record deleted successfully: ID->" + id);
    }
}