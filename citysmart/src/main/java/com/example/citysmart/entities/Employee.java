package com.example.citysmart.entities;

import java.time.ZonedDateTime;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employees")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int id;

    @NotNull(message = "Employee name is required")
    @Column(name = "employee_name")
    private String employeename;

    @NotNull(message = "Department name is required")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Department name must be alphanumeric value")
    @Size(min=2, max=50, message = "Department name must be between 2 and 50 characters")
    @Column(name = "department")
    private String department;

    @Column(name = "joining_date")
    private Date joiningdate;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Min age is 18")
    @Max(value = 68, message = "Max age is 68")
    @Column(name = "age")
    private short age;

    @Column(name = "address")
    private String address;

    @Column(name = "salary")
    private BigDecimal salary;

    // alternative data type: ZonedDateTime
    @Column(name = "left_date")
    private Timestamp leftdate;

    @Column(name = "left_job")
    private boolean leftjob;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getJoiningdate() {
        return joiningdate;
    }

    public void setJoiningdate(Date joiningdate) {
        this.joiningdate = joiningdate;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Timestamp getLeftdate() {
        return leftdate;
    }

    public void setLeftdate(Timestamp leftdate) {
        this.leftdate = leftdate;
    }

    public boolean isLeftjob() {
        return leftjob;
    }

    public void setLeftjob(boolean leftjob) {
        this.leftjob = leftjob;
    }



}
