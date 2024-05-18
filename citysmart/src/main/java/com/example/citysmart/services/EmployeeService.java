package com.example.citysmart.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.citysmart.entities.Employee;
import com.example.citysmart.repositories.EmployeeRepository;

@Service
public class EmployeeService {
    
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeService(){}

    public List<Employee> saveAllEmployees(List<Employee> employees){
        return employeeRepository.saveAll(employees);
    }

    public List<Employee> findAllEmployees(){
        return employeeRepository.findAll();
    }    

   public List<Employee> findAllEmplyeesByIds(List<Integer> ids){
        return employeeRepository.findAllById(ids);
   } 

   public Employee findEmployeeById(int id){
        return employeeRepository.findById(id);
   }

   public List<Employee> findEmployeeByName(String employeename){
        return employeeRepository.findByEmployeename(employeename);
   }

   public List<Employee> findByDepartmentAndAgeLessThan(String department, short age){
     return employeeRepository.findByDepartmentAndAgeLessThan(department, age);
   }

   public List<Employee> findByEmployeenameStartingWith(String employee){
        return employeeRepository.findByEmployeenameStartingWith(employee);
   }

  public List<Employee> findByEmployeenameContaining(String employee){
        return employeeRepository.findByEmployeenameContaining(employee);
  } 


  public Employee getEmployeeById(int id){
     return employeeRepository.getByIdJPQL(id);
  }  

  public List<Object[]> getByEmployeenameStartingWithJQPL(String prefix){
     return employeeRepository.getByEmployeenameStartingWithJQPL(prefix);
  }
  public List<Object[]> getByEmployeenameStartingWithNative(String prefix){
     return employeeRepository.getByEmployeenameStartingWithNative(prefix);
  }

  public List<Employee> getAllEmplyeesByIdsJPQL(String ids){
     return employeeRepository.getAllEmplyeesByIdsJPQL(ids);
  }
  
  public int NqGetTotalEmployees(){
     return employeeRepository.NqgetTotalEmployees();
  }

  public int NqGetEmployeeAge(int id){
     short age=0;
     return employeeRepository.NqGetEmployeeAge(id, age);
  }

  public int ProcGetGetEmployeeAge(int id){
     return employeeRepository.ProcGetGetEmployeeAge(id);
  }


}

