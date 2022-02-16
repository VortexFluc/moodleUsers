package com.moodle.info.users.models;

import com.moodle.info.users.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseModel {
    private Long courseId;
    private String courseName;
    private String courseShortname;
    private LocalDate startTime;

    public static CourseModel toModel(Course course) {
        CourseModel model = new CourseModel();
        model.setCourseId(course.getCourseId());
        model.setCourseName(course.getCourseName());
        model.setCourseShortname(course.getCourseShortname());
        model.setStartTime(course.getStartTime());
        return model;
    }
}
