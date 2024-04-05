package com.example;

public class Employee {

    private int ID;
    private String Name;
    private int Salary;

    public Employee() {
    }

    public Employee(String Name, int Salary) {
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