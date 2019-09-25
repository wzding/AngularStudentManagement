package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.StdCourse;
import com.mycompany.myapp.domain.dto.CourseDto;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.repository.StdCourseRepository;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StdCourseRepository stdCourseRepository;

    @Autowired
    private UserService userService;

    List<CourseDto> courseDtos = new ArrayList<>();

    public List<CourseDto> findAllCourses() {

        //Cache
        if (courseDtos.isEmpty()) {
            List<Course> courses = courseRepository.findAll();

            for (Course c : courses) {
                courseDtos.add(new CourseDto(c.getCourseName(), c.getCourseLocation(), c.getCourseContent(), c.getTeacherId()));
            }

            return courseDtos;
        }

        return courseDtos;
    }

    public List<CourseDto> findAllCoursesDtoFromDB(){
        return courseRepository.findAllCoursesDto();
    }

    public List<CourseDto> findAllCoursesDtoWithTeacherNameFromDB(){
        return stdCourseRepository.findAllCoursesDtoWithTeacherName();
    }

    public void addCourse(CourseDto course) throws Exception{
        Course courseBeingSaved = Course.builder()
            .courseName(course.getCourseName())
            .courseContent(course.getCourseContent())
            .courseLocation(course.getCourseLocation())
            .teacherId(course.getTeacherId())
            .build();
        try {
            courseRepository.saveAndFlush(courseBeingSaved);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void registerCourse(String courseName) throws Exception{
        Optional<Course> OptionalExistingCourse = courseRepository.findCourseByCourseName(courseName);
        Course course = OptionalExistingCourse.get();
        StdCourse courseBeingSaved = StdCourse.builder()
            .courseName(course.getCourseName())
            .courseContent(course.getCourseContent())
            .courseLocation(course.getCourseLocation())
            .teacherId(course.getTeacherId())
            .build();

        try {
            stdCourseRepository.saveAndFlush(courseBeingSaved);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }
    public void deleteCourse(String courseName) throws Exception{
        Optional<Course> OptionalExistingCourse = courseRepository.findCourseByCourseName(courseName);
        try {
            courseRepository.delete(OptionalExistingCourse.get());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void cancelCourse(String courseName) throws Exception{
        Optional<StdCourse> OptionalExistingCourse = stdCourseRepository.findCourseByCourseName(courseName);

        try {
            stdCourseRepository.delete(OptionalExistingCourse.get());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void updateCourse(CourseDto course) throws Exception{
        Optional<Course> OptionalExistingCourse = courseRepository.findCourseByCourseName(course.getCourseName());

        if(!OptionalExistingCourse.isPresent()){
            throw new Exception("Course is not exist.");
        }

        Course existingCourse = OptionalExistingCourse.get();
        existingCourse.setCourseContent(course.getCourseContent());
        existingCourse.setCourseLocation(course.getCourseLocation());
        existingCourse.setCourseName(course.getCourseName());
        existingCourse.setTeacherId(course.getTeacherId());

    }



}
