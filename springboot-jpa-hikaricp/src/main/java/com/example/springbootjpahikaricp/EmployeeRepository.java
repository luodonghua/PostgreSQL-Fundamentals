
package com.example.springbootjpahikaricp;

import com.example.springbootjpahikaricp.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}