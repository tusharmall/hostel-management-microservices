package com.microservices.course_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Course name cannot be empty")
    @Size(min = 5, max = 100,
            message = "Course name must contain between 5 and 100 characters")
    private String name;

    @Min(value = 1,
            message = "Duration should be at least 1 month")
    @Max(value = 60,
            message = "Duration cannot exceed 60 months")
    private int durationInMonths;

    public Course() {
    }

    public Course(Integer id,
                  String name,
                  int durationInMonths) {
        this.id = id;
        this.name = name;
        this.durationInMonths = durationInMonths;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }

    public void setDurationInMonths(int durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", durationInMonths=" + durationInMonths + "]";
	}
}




// package com.microservices.course_service.entity;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.Id;
// import jakarta.validation.constraints.Size;

// @Entity
// public class Course {
//     @Id
//     @GeneratedValue
//     private int id;
    
//     @Size(min = 5, message = "Course name should have at least 5 characters")
//     private String name;

//     private int durationInMonths;

//     public Course() {}

//     public Course(int id, String name, int durationInMonths) {
//         this.id = id;
//         this.name = name;
//         this.durationInMonths = durationInMonths;
//     }

// 	public int getId() {
// 		return id;
// 	}

// 	public void setId(int id) {
// 		this.id = id;
// 	}

// 	public String getName() {
// 		return name;
// 	}

// 	public void setName(String name) {
// 		this.name = name;
// 	}

// 	public int getDurationInMonths() {
// 		return durationInMonths;
// 	}

// 	public void setDurationInMonths(int durationInMonths) {
// 		this.durationInMonths = durationInMonths;
// 	}

// 	@Override
// 	public String toString() {
// 		return "Course [id=" + id + ", name=" + name + ", durationInMonths=" + durationInMonths + "]";
// 	}

//     // Getters and Setters
//     // toString() method
    
// }
