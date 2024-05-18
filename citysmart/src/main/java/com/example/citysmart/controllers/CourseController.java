package com.example.citysmart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.citysmart.entities.Course;
import com.example.citysmart.pojos.CourseRequest;
import com.example.citysmart.services.CourseService;

@RestController
public class CourseController {
    
    @Autowired
    CourseService courseService;

    @PostMapping("addcourse")
    public Course addCourse(@RequestBody CourseRequest courserequest){
        return courseService.addCourseWithContents(courserequest);
    }

}
