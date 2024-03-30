
package com.example.springbootjpahikaricp;

import com.example.springbootjpahikaricp.Employee;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    
    //Example to demonstrate native query
    @Query(value = "SELECT * FROM employee e WHERE e.name = :name", nativeQuery = true)
    List<Employee> findByyEmployeeNameNative(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO employee (id, name, salary) values (:id, :name, :salary)", nativeQuery = true)
    void insertEmployeeNative(@Param("id") int id, @Param("name") String name, @Param("salary") int salary);

    @Modifying
    @Transactional
    @Query(value = "UPDATE employee e SET salary = ? WHERE e.id = ?", nativeQuery = true)
    int UpdateEmployeeSalaryByIDNative(int salary, int id);


    //Example to demonstrate JPQL

    @Query(value = "SELECT e FROM Employee e WHERE e.Name = :name")
    List<Employee> findByyEmployeeNameJPQLOrderByIDDesc(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Employee e SET Salary = :salary WHERE e.ID = :id")
    int UpdateEmployeeSalaryByIDJPQL(@Param("salary") Integer Salary, @Param("id") Integer ID);
}