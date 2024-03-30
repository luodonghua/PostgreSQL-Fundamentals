package com.example;

import java.io.File;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.MutationQuery;


public class HibernatePgSQL {

    public static void main(String[] args) {

        String HibernateConfFile = "hibernate.cfg.xml";
        ClassLoader classLoader = HibernatePgSQL.class.getClassLoader();
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
        // Clear session cache, as NativeSQL update is not known to Entities
        // Clear is safe for new session and testing, use with care
        // setHibernateFlushMode(FlushMode.ALWAYS) doesn't always fix this issue
        session.clear(); 
        ReadEmployeeRecord(session);

        // DELETE
        DeleteEmployeeRecord(session);
        
        session.close();  
    }

    private static void CreateEmployeeRecord(Session session) {

        session.beginTransaction();
        String sql = "insert into employee (id,name,salary) values (:id,:name,:salary)";
        MutationQuery query = session.createNativeMutationQuery(sql);
        query.setParameter("id", 1);
        query.setParameter("name", "Employee-Demo");
        query.setParameter("salary", 5000);
        int rowCount = query.executeUpdate();
        session.getTransaction().commit();
        System.out.println("Record created successfully: " + rowCount + " rows");
    }

    private static void ReadEmployeeRecord(Session session) {

        String sql = "select id, name, salary from employee order by salary limit 5";
        List<Employee>  Employees= session.createNativeQuery(sql,
            Employee.class)
            .addSynchronizedEntityClass(Employee.class)
            .getResultList();
            System.out.println("Reading employee records: (limit to maximum 5)");
            for (Employee EmployeeObject : Employees) {
                // session.refresh(EmployeeObject);
                System.out.format("| %-2d | %-8s | %6d |%n",
                            EmployeeObject.getID(),
                            EmployeeObject.getName(),
                            EmployeeObject.getSalary());
            }
 
    }


    private static void UpdateEmployeeRecord(Session session) {

        session.beginTransaction();
        String sql = "UPDATE employee SET salary = :salary WHERE id = :id";
        MutationQuery query = session.createNativeMutationQuery(sql);
        query.setParameter("id", 1);
        query.setParameter("salary", 6000);
        int rowCount = query.executeUpdate();
        session.getTransaction().commit();
        System.out.println("Record update successfully: " + rowCount + " rows");

    }

    private static void DeleteEmployeeRecord(Session session) {

        session.beginTransaction();
        String sql = "DELETE FROM employee WHERE id >= :id";
        MutationQuery query = session.createNativeMutationQuery(sql);
        query.setParameter("id", 1);
        int rowCount = query.executeUpdate();
        session.getTransaction().commit();
        System.out.println("Record delete successfully: " + rowCount + " rows");
    }


}
