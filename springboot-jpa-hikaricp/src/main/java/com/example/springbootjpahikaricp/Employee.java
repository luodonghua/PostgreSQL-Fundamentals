package com.example.springbootjpahikaricp;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer ID;

    @Column(name = "name", nullable = false)
    private String Name;

    @Column(name = "salary", nullable = false)
    private Integer Salary;

    public Employee() {
	}

    public Employee(String Name, Integer Salary) {
        this.Name = Name;
        this.Salary = Salary;
    }
    public Employee(Integer ID, String Name, Integer Salary) {
        this.ID = ID;
        this.Name = Name;
        this.Salary = Salary;
    }

    public Integer getID() {
        return ID;
    }

    public void setId(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Integer getSalary() {
        return Salary;
    }

    public void setSalary(Integer Salary) {
        this.Salary = Salary;
    }
    
    public String toString(){
        return "ID: "+ID+" Name: "+Name+" Salary: "+Salary;
    }
}