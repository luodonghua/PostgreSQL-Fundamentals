package com.example.springbootjpahikaricp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootjpahikaricp.Employee;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/employees")
	public List<Employee> GetAllEmployees() {
		return employeeRepository.findAll();
	}

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> GetEmployeeById(@PathVariable(value = "id") Integer ID)
        throws ResourceNotFoundException {
        
        Employee employee = employeeRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("No such employee:" + ID));
        return ResponseEntity.ok().body(employee);
    }   

	@PostMapping("/employees")
	public Employee CreateEmployee(@Valid @RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}    

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Integer ID,
			@Valid @RequestBody Employee Employee1) 
        throws ResourceNotFoundException {
		
        Employee employee = employeeRepository.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("No such employee:" + ID));

		employee.setName(Employee1.getName());
		employee.setSalary(Employee1.getSalary());
		final Employee updatedEmployee1 = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee1);
	}    

	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Integer ID)
		throws ResourceNotFoundException {
		
        Employee employee = employeeRepository.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("No such employee:" + ID));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}    

}
