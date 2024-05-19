package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*

 * N+1 selection problem is a performance issue in Hibernate
 * For example, Employees and Departments have many-to-one relationship
 * one parent (Departments) call features many children (Employees)
 * 
 * 
 */


public class Main {

    public static void main(String[] args) {
        String HibernateConfFile = "hibernate.cfg.xml";
        ClassLoader classLoader = Main.class.getClassLoader();
        File f = new File(classLoader.getResource(HibernateConfFile).getFile());
        SessionFactory sessionFactory = new Configuration().configure(f).buildSessionFactory();
        Session session = sessionFactory.openSession();
        
        initialData(session);
        session.clear();
        NPlusOneProblemDemo(session);
        session.clear();
        NPlusOneProblemFixDemo(session);
    }

    private static void initialData(Session session) {

        List<String> deptList = new ArrayList<String>();
        deptList.add("IT");
        deptList.add("Sales");
        
        session.beginTransaction();
        deptList.forEach((deptName) -> {
            Department department = new Department();
            department.setName(deptName);
            session.persist(department);
            for (int i = 0; i < 5; i++) {
                Employee employee = new Employee();
                employee.setName("Employee " + department.getName() + ' ' + i);
                employee.setDepartment(department);
                session.persist(employee);
            }
        });
        session.getTransaction().commit();

    }

    private static void NPlusOneProblemDemo(Session session) {

        List<Department>  Departments= session.createQuery("FROM Department",
        Department.class).getResultList();

            for (Department DepartmentObject : Departments) {
                System.out.format("| %-2d | %-8s |%n",
                DepartmentObject.getId(),
                DepartmentObject.getName());
                System.out.println("Employees:");
                DepartmentObject.getEmployees().forEach((employee)->{
                    System.out.format("| %-2d | %-10s|%n",
                    employee.getId(),
                    employee.getName());
                });
            }
    }

    private static void NPlusOneProblemFixDemo(Session session) {

        List<Department>  Departments= session.createQuery("FROM Department d JOIN fetch d.employees",
        Department.class).getResultList();

            for (Department DepartmentObject : Departments) {
                System.out.format("| %-2d | %-8s |%n",
                DepartmentObject.getId(),
                DepartmentObject.getName());
                System.out.println("Employees:");
                DepartmentObject.getEmployees().forEach((employee)->{
                    System.out.format("| %-2d | %-10s|%n",
                    employee.getId(),
                    employee.getName());
                });
            }
    }

}

