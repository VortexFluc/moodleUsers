package com.moodle.info.users.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "course_id", unique = true) private Long courseId;
    @Column(name = "course_name") private String courseName;
    @Column(name = "course_shortname") private String courseShortname;
    @Column(name = "start_time") private LocalDate startTime;
}
