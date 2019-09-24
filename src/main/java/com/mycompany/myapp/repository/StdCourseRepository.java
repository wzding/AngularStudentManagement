package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.dto.CourseDto;
import com.mycompany.myapp.domain.dto.CourseWithTNDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StdCourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT new com.mycompany.myapp.domain.dto.CourseDto(s.courseName, s.courseLocation, s.courseContent, s.teacherId) from StdCourse s")
    List<CourseWithTNDto> findAllCoursesDtoWithTeacherName();

    Optional<Course> findCourseByCourseName(String courseName);

}
