package com.example.citysmart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.citysmart.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{

    public Course findById(int id);
}
