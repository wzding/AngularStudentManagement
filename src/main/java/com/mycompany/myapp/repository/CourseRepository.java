package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.dto.CourseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Cacheable;
import java.util.List;
import java.util.Optional;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
//    @Query("SELECT new com.mycompany.myapp.domain.dto.CourseDto(c.courseName, c.courseLocation, c.courseContent, c.teacherId) from Course c WHERE c.name = :courseName")
//    CourseDto findCourseByCourseName(String courseName);

    @Query("SELECT new com.mycompany.myapp.domain.dto.CourseDto(c.courseName, c.courseLocation, c.courseContent, c.teacherId) from Course c WHERE c.courseName = :courseName")
    CourseDto findCourseByName(String courseName);

    @Query("SELECT new com.mycompany.myapp.domain.dto.CourseDto(c.courseName, c.courseLocation, c.courseContent, c.teacherId) from Course c")
    List<CourseDto> findAllCoursesDto();

    Optional<Course> findCourseByCourseName(String courseName);

}
