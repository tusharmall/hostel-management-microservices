package com.microservices.course_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springdoc.core.annotations.ParameterObject;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.microservices.course_service.dto.CourseRequestDTO;
import com.microservices.course_service.dto.CourseResponseDTO;
import com.microservices.course_service.entity.Course;
import com.microservices.course_service.service.CourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService service;

    @Operation(summary = "Get All Courses", description = "Returns paginated courses")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Courses retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    @GetMapping
    public ResponseEntity<Page<CourseResponseDTO>> getCourses(
            @ParameterObject @PageableDefault(page = 0, size = 10, sort = "id")
            Pageable pageable) {
        return ResponseEntity.ok(service.getCourses(pageable));
    }
    //@GetMapping
    // public ResponseEntity<Page<Course>> getCourses(
    //         @ParameterObject
    //         @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {

    //     return ResponseEntity.ok(service.getCourses(pageable));
    // }
    // @GetMapping
    // public List<Course> getCourses() {
    //     return service.getAllCourses();
    // }
    
    

    @Operation(summary = "Get Course By Id", description = "Returns one course using course id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course Found"),
        @ApiResponse(responseCode = "404", description = "Course Not Found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable int id) {
        return ResponseEntity.ok(service.getCourseById(id));
    }
    // @GetMapping("/{id}")
    // public ResponseEntity<Course> getCourseById(@PathVariable int id) {
    //     return ResponseEntity.ok(service.getCourseById(id));
    // }

    @Operation(summary = "Create Course", description = "Creates a new course")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Course created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    // @PostMapping
    // public ResponseEntity<Course> addCourse(
    //         @Valid
    //         @RequestBody
    //         Course course) {

    //     Course savedCourse = service.saveCourse(course);

    //     return ResponseEntity
    //             .status(HttpStatus.CREATED)
    //             .body(savedCourse);
    // }
    @PostMapping
    public ResponseEntity<CourseResponseDTO> addCourse(
            @Valid @RequestBody CourseRequestDTO request) {
    
        CourseResponseDTO created = service.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update Course", description = "Updates an existing course by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course updated successfully"),
        @ApiResponse(responseCode = "404", description = "Course not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(
            @PathVariable int id,
            @Valid @RequestBody CourseRequestDTO request) {
        return ResponseEntity.ok(service.updateCourse(id, request));
    }
    // @PutMapping("/{id}")
    // public ResponseEntity<Course> updateCourse(@PathVariable int id, @Valid @RequestBody Course course) {
    //     return ResponseEntity.ok(service.updateCourse(id, course));
    // }

    @Operation(summary = "Delete Course", description = "Deletes a course by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable int id) {
        service.deleteCourse(id);
        return "Course deleted successfully";
    }

    // method for search api 
    @Operation(summary = "Search Courses by Name", description = "Returns courses matching the search name (case-insensitive)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search results retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    })
    // @GetMapping("/search")
    // public ResponseEntity<List<Course>> searchByName(@RequestParam String name) {
    //     List<Course> courses = service.searchByName(name);
    //     return ResponseEntity.ok(courses);
    // }
    @GetMapping("/search")
    public ResponseEntity<List<CourseResponseDTO>> searchByName(@RequestParam String name) {
        List<CourseResponseDTO> courses = service.searchByName(name);
        return ResponseEntity.ok(courses);
    }

    
    @Operation(summary = "Search Courses by Duration", description = "Returns courses with specified duration in months")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search results retrieved")
    })
    // @GetMapping("/search-duration")
    // public ResponseEntity<List<Course>> searchByDuration(@RequestParam int duration) {
    //     List<Course> courses = service.searchByDuration(duration);
    //     return ResponseEntity.ok(courses);
    // }
    @GetMapping("/search-duration")
    public ResponseEntity<List<CourseResponseDTO>> searchByDuration(@RequestParam int duration) {
        List<CourseResponseDTO> courses = service.searchByDuration(duration);
        return ResponseEntity.ok(courses);
    }
    
    @Operation(summary = "Search Courses by Name and Duration", description = "Returns courses matching both name and duration criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search results retrieved")
    })
    // @GetMapping("/search-advanced")
    // public ResponseEntity<List<Course>> searchByNameAndDuration(
    //         @RequestParam String name, 
    //         @RequestParam int duration) {
    //     List<Course> courses = service.searchByNameAndDuration(name, duration);
    //     return ResponseEntity.ok(courses);
    // }
    @GetMapping("/search-advanced")
    public ResponseEntity<List<CourseResponseDTO>> searchByNameAndDuration(
            @RequestParam String name,
            @RequestParam int duration) {
        List<CourseResponseDTO> courses = service.searchByNameAndDuration(name, duration);
        return ResponseEntity.ok(courses);
    }


}