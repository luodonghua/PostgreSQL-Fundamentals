
package com.example.springboothibernateprocedure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    
    //Example to demonstrate procedures and functions

    @Procedure 
    void add_new_employee(String Name, int Salary);

    @Procedure (procedureName = "add_new_employee")
    void addNewEmployeeRecord(String Name, int Salary);

    @Modifying
    @Transactional
    @Query(value = "call add_new_employee(:name, :salary)", nativeQuery = true)
    void addNewEmployeeRecordNative(@Param("name") String name, @Param("salary") int salary);

    @Query(value = "SELECT get_salary_by_name(:name)", nativeQuery = true)
    Integer getSalaryByNameNative(@Param("name") String name);

    @Query(value = "SELECT get_salary_by_name(:name)")
    Integer getSalaryByNameJPQL(@Param("name") String name);

}