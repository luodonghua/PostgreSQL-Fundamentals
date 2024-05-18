package com.example.citysmart.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.citysmart.entities.Course;
import com.example.citysmart.entities.CourseContents;
import com.example.citysmart.pojos.CourseRequest;
import com.example.citysmart.repositories.CourseContentsRepository;
import com.example.citysmart.repositories.CourseRepository;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;
    
    @Autowired
    CourseContentsRepository courseContentsRepository;

    public CourseService() {}


    public Course addCourseWithContents(CourseRequest courserequest) {

        Course course = new Course();
        course.setId(courserequest.id);
        course.setCoursename(courserequest.coursename);
        course.setCoursecontents(courserequest.coursecontents.stream().map(coursecontent->{
            CourseContents ccontents = coursecontent;
            if (ccontents.getId() > 0) {
                ccontents = courseContentsRepository.findById(ccontents.getId());
            }
            ccontents.addCourse(course);
            return ccontents;
        }).collect(Collectors.toSet()));

        return courseRepository.save(course);


    }

}

