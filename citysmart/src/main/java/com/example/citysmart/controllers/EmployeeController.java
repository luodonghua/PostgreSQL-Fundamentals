package com.example.citysmart.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.citysmart.commons.GlobalExceptionHandler;
import com.example.citysmart.entities.Employee;
import com.example.citysmart.services.EmployeeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("employees/")
@Validated

public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("addemplyees")
    public List<Employee> addAllEmployees(@Valid @RequestBody List<Employee> employees) {
        return employeeService.saveAllEmployees(employees);
    }

    @GetMapping("allemployees")
    public List<Employee> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("employeewithname")
    public List<Employee> getAllEmployeesWithName(@RequestParam String employeename) {
        return employeeService.findEmployeeByName(employeename);
    }

    @GetMapping("employeebyid")
    public Employee findEmployeeById(@RequestParam @Min(value=1, message="Employee ID should be greater than 0") int id) {
        return employeeService.findEmployeeById(id);
    }

    @GetMapping("allemployeesbyids")
    public List<Employee> findAllEmplyeesByIds(@RequestParam String ids) {
        return employeeService.findAllEmplyeesByIds(
            Arrays.asList(ids.split(",")).
            stream().map(Integer::parseInt).collect(Collectors.toList())
        );
    }
 
    @GetMapping("empbydeptandage")
    public List<Employee> getEmployeesByDeptAndAge(@RequestParam String department, @RequestParam short age) {
        return employeeService.findByDepartmentAndAgeLessThan(department, age);
    }

    @GetMapping("empbyname")
    public List<Employee> findByEmployeenameStartingWith(@RequestParam String employee) {
        return employeeService.findByEmployeenameStartingWith(employee);
    }

    @GetMapping("employeebyidjpql")
    public Employee getEmployeeById(@RequestParam int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("empprefixjpql")
    public List<Object[]> getByEmployeenameStartingWithJQPL(@RequestParam String prefix) {
        return employeeService.getByEmployeenameStartingWithJQPL(prefix);
    }

    @GetMapping("empprefixnative")
    public List<Object[]> getByEmployeenameStartingWithNative(@RequestParam String prefix) {
        return employeeService.getByEmployeenameStartingWithNative(prefix);
    }    

    @GetMapping("allemployeesbyidsJPQL")
    public List<Employee> getAllEmplyeesByIdsJPQL(@RequestParam String ids) {
        return employeeService.getAllEmplyeesByIdsJPQL(ids);
    }  

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintValidationException(org.hibernate.exception.ConstraintViolationException ex) {
        return new ResponseEntity<>("Error: "+ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("nqtotalemployees")
    public int NgGetTotalEmployees(){
        return employeeService.NqGetTotalEmployees();
    }

    @GetMapping("nqgetemployeeage")
    public int ProcGetTotalEmployees(@RequestParam int id){
        return employeeService.NqGetEmployeeAge(id);
    }

    @GetMapping("procgetemployeeage")
    public int ProcGetGetEmployeeAge(@RequestParam int id){
        return employeeService.ProcGetGetEmployeeAge(id);
    }

}
