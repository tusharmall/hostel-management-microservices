package com.microservices.course_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.microservices.course_service.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
       
    // Search by name (case-insensitive)
    List<Course> findByNameContainingIgnoreCase(String name);
    
    // Search by duration
    List<Course> findByDurationInMonths(int duration);
    
    // Search by name AND duration
    List<Course> findByNameContainingIgnoreCaseAndDurationInMonths(String name, int duration);

}
