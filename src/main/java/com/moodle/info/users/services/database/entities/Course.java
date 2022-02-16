package com.moodle.info.users.services.database.entities;

import java.time.LocalDate;

public class Course {
    private Long courseId;
    private String courseName;
    private String courseShortname;
    private LocalDate startTime;
    public Course (Long id, String fname, String sname, LocalDate date) {
        courseId = id;
        courseName = fname;
        courseShortname = sname;
        startTime = date;
    }
    @Override
    public String toString() {
        return "ID: " + courseId + ", Fullname: " + courseName + ", Shortname: " + courseShortname + ", StartDate: " +
                startTime;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseShortname() {
        return courseShortname;
    }

    public void setCourseShortname(String courseShortname) {
        this.courseShortname = courseShortname;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }
}
