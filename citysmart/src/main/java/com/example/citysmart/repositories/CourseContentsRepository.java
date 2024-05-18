package com.example.citysmart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.citysmart.entities.CourseContents;

@Repository
public interface CourseContentsRepository extends JpaRepository<CourseContents, Integer>{
    
    public CourseContents findById(int id);
}
