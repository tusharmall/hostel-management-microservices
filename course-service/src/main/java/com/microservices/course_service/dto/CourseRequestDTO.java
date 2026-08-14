package com.microservices.course_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CourseRequestDTO {

    @NotBlank(message = "Course name cannot be empty")
    @Size(min = 5, max = 100, message = "Course name must contain between 5 and 100 characters")
    private String name;

    @Min(value = 1, message = "Duration should be at least 1 month")
    @Max(value = 60, message = "Duration cannot exceed 60 months")
    private Integer durationInMonths;

    // getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDurationInMonths() {
        return durationInMonths;
    }

    public void setDurationInMonths(Integer durationInMonths) {
        this.durationInMonths = durationInMonths;
    }
    

}