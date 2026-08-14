package com.microservices.course_service.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.course_service.entity.Course;
import com.microservices.course_service.exception.CourseNotFoundException;
import com.microservices.course_service.feign.RoomClient;
import com.microservices.course_service.repository.CourseRepository;
import com.microservices.course_service.response.RoomResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microservices.course_service.dto.CourseRequestDTO;
import com.microservices.course_service.dto.CourseResponseDTO;

@Service
public class CourseService {


    private static final Logger logger =
        LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CourseRepository repository;

    @Autowired
    private RoomClient roomClient;

    // public List<Course> getAllCourses() {

    //     // List<RoomResponse> rooms = roomClient.getRooms();

    //     // System.out.println(rooms);
    //     logger.info("Fetching all courses from database");

    //     return repository.findAll();
    // }
    // public Page<Course> getCourses(Pageable pageable) {

    //      logger.info("Fetching paginated courses");
     
    //      return repository.findAll(pageable);

    // }
    // public Course getCourseById(int id) {
    //     logger.error("Course not found with id {}", id);
    //     return repository.findById(id)
    //             .orElseThrow(() -> new CourseNotFoundException("Course not found with id " + id));
    // }


    public Course saveCourse(Course course) {

        logger.info("Saving course {}", course.getName());
        return repository.save(course);
    }
    public CourseResponseDTO createCourse(CourseRequestDTO request) {
    Course course = mapToEntity(request);
    Course saved = repository.save(course);
    return mapToResponseDTO(saved);
    }
    

    public void deleteCourse(int id) {

        repository.deleteById(id);
    }
    // public Course updateCourse(int id, Course updatedCourse) {
    //     Course existingCourse = getCourseById(id);

    //     existingCourse.setName(updatedCourse.getName());
    //     existingCourse.setDurationInMonths(updatedCourse.getDurationInMonths());

    //     return repository.save(existingCourse);
    // }

    // for search api methods 
    // public List<Course> searchByName(String name) {
    //     logger.info("Searching courses by name: {}", name);
    //     return repository.findByNameContainingIgnoreCase(name);
    // }

    
    public List<CourseResponseDTO> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }
    
    public List<CourseResponseDTO> searchByDuration(int duration) {
        return repository.findByDurationInMonths(duration)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }
    
    public List<CourseResponseDTO> searchByNameAndDuration(String name, int duration) {
        return repository.findByNameContainingIgnoreCaseAndDurationInMonths(name, duration)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }
    
    public CourseResponseDTO getCourseById(int id) {
        Course course = repository.findById(id)
            .orElseThrow(() -> new CourseNotFoundException("Course not found with id " + id));
        return mapToResponseDTO(course);
    }
    
    public Page<CourseResponseDTO> getCourses(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToResponseDTO);
    }
    
    public CourseResponseDTO updateCourse(int id, CourseRequestDTO request) {
        Course existing = repository.findById(id)
            .orElseThrow(() -> new CourseNotFoundException("Course not found with id " + id));
    
        existing.setName(request.getName());
        existing.setDurationInMonths(request.getDurationInMonths());
    
        return mapToResponseDTO(repository.save(existing));
    }
    






    private CourseResponseDTO mapToResponseDTO(Course course) {
        return modelMapper.map(course, CourseResponseDTO.class);
    }
    
    private Course mapToEntity(CourseRequestDTO request) {
        return modelMapper.map(request, Course.class);
    }
    // replace with model mapping 


    // private CourseResponseDTO mapToResponseDTO(Course course) {
    //     CourseResponseDTO dto = new CourseResponseDTO();
    //     dto.setId(course.getId());
    //     dto.setName(course.getName());
    //     dto.setDurationInMonths(course.getDurationInMonths());
    //     return dto;
    // }
    
    // private Course mapToEntity(CourseRequestDTO request) {
    //     Course course = new Course();
    //     course.setName(request.getName());
    //     course.setDurationInMonths(request.getDurationInMonths());
    //     return course;
    // }

}