package com.example.citysmart.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.citysmart.entities.Employee;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Integer>{
    Employee findById(int id);
    List<Employee> findByEmployeename(String employeename);

    List<Employee> findByDepartmentAndAgeLessThan(String department, short age);

    List<Employee> findByEmployeenameStartingWith(String employee);

    @Query("from Employee where id = ?1")
    Employee getByIdJPQL(String id);

    @Query("select e.id, e.employeename, e.department from Employee e where e.employeename like :prefix% order by id")
    List<Object[]> getByEmployeenameStartingWithJQPL(@Param("prefix") String prefix);

    @Query(value = "select e.employee_id, e.employee_name, e.department from employees e "+
                    "where e.employee_name like :prefix% order by employee_id", 
                    nativeQuery = true)
    List<Object[]> getByEmployeenameStartingWithNative(@Param("prefix") String prefix);

    @Query(value = "select * from employees where employee_id = any (cast(string_to_array(?1,',') as int[]))", nativeQuery = true)
    List<Employee> getAllEmplyeesByIdsJPQL(String ids);

    // Not implemented in the Service and Controller
    List<Employee> findByEmployeenameContaining(String empployee);
    List<Employee> findTop5ByAge(short age);
    List<Employee> findByAgeBetween(short startage, short endage);
    List<Employee> findByAgeIn(List<Short> ages);
    List<Employee> findByJoiningdateAfter(Date date);
    List<Employee> findByJoiningdateBefore(Date date);
    List<Employee> findByJoiningdateBetween(Date startdate, Date enddate);
    List<Employee> findByJoiningdateBetweenAndDepartment(Date startdate, Date enddate, String department);
    List<Employee> findByLeftdateIsNull();
    List<Employee> findByLeftdateIsNotNull();
    
    List<Employee> findByEmployeenameEquals(String employeename);
    List<Employee> findByEmployeenameIsNot(String employeename);
    List<Employee> findByEmployeenameOrderByJoiningdateAsc(String employee);
    List<Employee> findByEmployeenameOrderByJoiningdateDesc(String employee);

    List<Employee> findByLeftjobTrue();
    List<Employee> findByLeftjobFalse();
    List<Employee> findByLeftjob(boolean leftjob);

    @Query(value = "select gettotalemployees()", nativeQuery = true)
    int NqgetTotalEmployees();

    // @Query(value = "call procgetemployeeage(?1,cast(0 as smallint))", nativeQuery = true)
    // int NqGetEmployeeAge(int employee_id);
    @Query(value = "call procgetemployeeage(?1,?2)", nativeQuery = true)
    int NqGetEmployeeAge(int employee_id, short age);

    // output parameter type depends on the return type
    // here short maps to SQL smallint
    @Procedure(procedureName = "procgetemployeeage", outputParameterName="v_age")
    short ProcGetGetEmployeeAge(int employee_id);

}
