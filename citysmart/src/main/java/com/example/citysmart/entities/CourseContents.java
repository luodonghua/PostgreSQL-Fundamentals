package com.example.citysmart.entities;


import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;



@Entity
@Table (name = "course_contents")
public class CourseContents {
    

    @Id
    @Column (name= "content_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column (name= "content_name")
    private String contentname;

    @JsonIgnore
    @ManyToMany(mappedBy = "coursecontents")
    private Set<Course> courses = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContentname() {
        return contentname;
    }

    public void setContentname(String contentname) {
        this.contentname = contentname;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }


    public void addCourse(Course course){
        this.courses.add(course);
    }


}
