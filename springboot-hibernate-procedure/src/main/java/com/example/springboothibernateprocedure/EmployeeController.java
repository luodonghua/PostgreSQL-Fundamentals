package com.example.springboothibernateprocedure;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Null;



@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/employees")
	public List<Employee> GetAllEmployees() {
		return employeeRepository.findAll();
	}

    @GetMapping("/insertemployees")
    public String insertEmployeeNativeController(){
		employeeRepository.add_new_employee("Employee-1",3000);
		employeeRepository.addNewEmployeeRecord("Employee-2",4000);
		employeeRepository.addNewEmployeeRecordNative("Employee-3",5000);
        return "Employees add successfully";
    }

	@GetMapping("/getsalarybynamenative/{name}")
    public int getSalaryByNameNativeController(@PathVariable(value = "name") String Name) {
        
        int salary = employeeRepository.getSalaryByNameNative(Name);
        return salary;
    }

	@GetMapping("/getsalarybynamejpql/{name}")
    public int getSalaryByNameJPQLController(@PathVariable(value = "name") String Name) {
        
		int salary = employeeRepository.getSalaryByNameJPQL(Name);
        return salary;
    }   
}
