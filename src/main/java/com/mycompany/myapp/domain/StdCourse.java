package com.mycompany.myapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "std_course")
@Data
public class StdCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, columnDefinition = "bigint")
    private long id;

    @Column(name = "course_name", nullable = false, length = 100, columnDefinition = "nvarchar(100)")
    private String courseName;

    @Column(name = "course_location", nullable = false, length = 30, columnDefinition = "nvarchar(30)")
    private String courseLocation;

    @Column(name = "course_content", nullable = false, length = 200, columnDefinition = "nvarchar(200)")
    private String courseContent;

    @Column(name = "teacher_id", nullable = false, columnDefinition = "bigint")
    private long teacherId;
}
