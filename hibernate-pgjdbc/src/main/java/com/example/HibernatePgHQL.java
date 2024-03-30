package com.example;

import java.io.File;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.MutationQuery;

public class HibernatePgHQL {

    public static void main(String[] args) {

        String HibernateConfFile = "hibernate.cfg.xml";
        ClassLoader classLoader = HibernatePgHQL.class.getClassLoader();
        File f = new File(classLoader.getResource(HibernateConfFile).getFile());
        SessionFactory sessionFactory = new Configuration().configure(f).buildSessionFactory();
        Session session = sessionFactory.openSession();

        // CREATE
        CreateEmployeeRecord(session);
        
        // READ
        ReadEmployeeRecord(session);

        // UPDATE
        UpdateEmployeeRecord(session);
        
        // READ
        ReadEmployeeRecord(session);

        // DELETE
        DeleteEmployeeRecord(session);
        
        session.close();  
    }

    private static void CreateEmployeeRecord(Session session) {

        session.beginTransaction();
        String hql = "INSERT INTO Employee (ID, Name, Salary) values (:id,:name,:salary)";
        MutationQuery query = session.createMutationQuery(hql);
        query.setParameter("id", 1);
        query.setParameter("name", "Employee-Demo");
        query.setParameter("salary", 5000);
        int rowCount = query.executeUpdate();
        session.getTransaction().commit();
        System.out.println("Record created successfully: " + rowCount + " rows");
    }

    private static void ReadEmployeeRecord(Session session) {
        String hql = "SELECT Name, Salary FROM Employee WHERE ID=1";
        List<Employee>  Employees= session.createSelectionQuery(hql,
            Employee.class).setMaxResults(5).getResultList();
        System.out.println("Reading employee records: (limit to maximum 5)");
        for (Employee EmployeeObject : Employees) {
            System.out.format("| %-8s | %6d |%n",
                        EmployeeObject.getName(),
                        EmployeeObject.getSalary());
        }
    }

    private static void UpdateEmployeeRecord(Session session) {

        session.beginTransaction();
        String hql = "UPDATE Employee SET Salary = :salary WHERE ID = :id";
        MutationQuery query = session.createMutationQuery(hql);
        query.setParameter("id", 1);
        query.setParameter("salary", 6000);
        int rowCount = query.executeUpdate();
        session.getTransaction().commit();
        System.out.println("Record update successfully: " + rowCount + " rows");

    }   
    
    private static void DeleteEmployeeRecord(Session session) {

        session.beginTransaction();
        String hql = "DELETE Employee WHERE ID = :id";
        MutationQuery query = session.createMutationQuery(hql);
        query.setParameter("id", 1);
        int rowCount = query.executeUpdate();
        session.getTransaction().commit();
        System.out.println("Record delete successfully: " + rowCount + " rows");
    }


}
