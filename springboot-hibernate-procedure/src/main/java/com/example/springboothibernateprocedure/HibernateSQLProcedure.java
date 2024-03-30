package com.example.springboothibernateprocedure;

import java.io.File;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.procedure.ProcedureCall;


import jakarta.persistence.ParameterMode;


public class HibernateSQLProcedure {

    public static void main(String[] args) {

        String HibernateConfFile = "hibernate.cfg.xml";
        ClassLoader classLoader = HibernateSQLProcedure.class.getClassLoader();
        File f = new File(classLoader.getResource(HibernateConfFile).getFile());
        SessionFactory sessionFactory = new Configuration().configure(f).buildSessionFactory();
        Session session = sessionFactory.openSession();


        // Clean Up the table
        truncateEmployeeRecord(session);

        // READ
        addNewEmployeeRecord(session);

        // UPDATE
        getSalaryByName(session);
   
        // READ
        ReadEmployeeRecord(session);

        session.close();  
    }

    private static void truncateEmployeeRecord(Session session) {

        session.beginTransaction();
        String sql = "truncate table employee";
        session.createNativeMutationQuery(sql).executeUpdate();
        session.getTransaction().commit();
        System.out.println("Table truncated successfully.");
    }

 
    private static void addNewEmployeeRecord(Session session) {

        session.beginTransaction();
        ProcedureCall query = session.createStoredProcedureCall("add_new_employee");
        query.registerStoredProcedureParameter("name",String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("salary",Integer.class, ParameterMode.IN);
        query.setParameter("name", "Employee-1");
        query.setParameter("salary", 3000);
        query.execute();
        session.getTransaction().commit();
        System.out.println("Record created successfully.");
    }

    private static void getSalaryByName(Session session) {

       int Salary= session.createNativeQuery("select get_salary_by_name(:name)",Integer.class)
            .addSynchronizedEntityClass(Employee.class)
            .setParameter("name","Employee-1")
            .getSingleResult();
        System.out.println("Employee-1 salary is " + Salary);
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

}
