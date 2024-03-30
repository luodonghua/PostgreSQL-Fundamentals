package com.example;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    
    private static final long serialVersionUID = 1L;
   
    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id")
    private Integer ID;
   
    @Column(name = "name", nullable = false, length = 30)
    private String Name;
   
    @Column(name = "salary", nullable = false)
    private Integer Salary;

    public Employee() {
    }

    public Employee(String Name, Integer Salary) {
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