package com.example;

import java.io.File;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernatePgJdbcCURD {
    public static void main(String[] args) {

        String HibernateConfFile = "hibernate.cfg.xml";
        ClassLoader classLoader = HibernatePgJdbcCURD.class.getClassLoader();
        File f = new File(classLoader.getResource(HibernateConfFile).getFile());
        SessionFactory sessionFactory = new Configuration().configure(f).buildSessionFactory();
        Session session = sessionFactory.openSession();

        // CREATE
        CreateEmployeeRecord(session);
        // READ
        ReadEmployeeRecord(session);
        // UPDATE
        UpdateEmployeeRecord(session);
        // DELETE
        DeleteEmployeeRecord(session);
        
        session.close();
    }

    private static void CreateEmployeeRecord(Session session) {
        Employee Employee1 = new Employee();
        Employee1.setName("Employee-Demo");
        Employee1.setSalary(5000);
        session.beginTransaction();
        session.persist(Employee1);
        session.getTransaction().commit();
        System.out.println("Record created successfully: " + Employee1);
    }

    private static void ReadEmployeeRecord(Session session) {
        List<Employee>  Employees= session.createQuery("FROM Employee",Employee.class).setMaxResults(5).getResultList();
        System.out.println("Reading employee records: (limit to maximum 5)");
        for (Employee EmployeeObject : Employees) {
            System.out.format("| %-2d | %-8s | %6d |%n",
                        EmployeeObject.getID(),
                        EmployeeObject.getName(),
                        EmployeeObject.getSalary());
        }
    }

    private static void UpdateEmployeeRecord(Session session) {
        int id = 1;
        Employee Employee1 = (Employee) session.get(Employee.class, id);
        Employee1.setSalary(6000);
        session.beginTransaction();
        session.merge(Employee1);
        session.getTransaction().commit();
        System.out.println("Record updated successfully: " + Employee1);
    }

    private static void DeleteEmployeeRecord(Session session) {
        int id = 1;
        Employee Employee1 = (Employee) session.get(Employee.class, id);
        session.beginTransaction();
        session.remove(Employee1);
        session.getTransaction().commit();
        System.out.println("Record deleted successfully: " + Employee1);
    }


}
