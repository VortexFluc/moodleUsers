package com.moodle.info.users.services.database.entities;

import java.time.LocalDate;

public class Time {
    private Long id;
    private Long courseId;
    private boolean isActivated;
    private LocalDate enrolTime;
    private LocalDate activationTime;
    private LocalDate gradedTime;
    private Long learningPlanId;

    public Time(Long id, Long courseId, boolean isActivated, LocalDate enrolTime, LocalDate activationTime, LocalDate gradedTime, Long learningPlanId) {
        this.id = id;
        this.courseId = courseId;
        this.isActivated = isActivated;
        this.enrolTime = enrolTime;
        this.activationTime = activationTime;
        this.gradedTime = gradedTime;
        this.learningPlanId = learningPlanId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public LocalDate getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(LocalDate activationTime) {
        this.activationTime = activationTime;
    }

    public LocalDate getGradedTime() {
        return gradedTime;
    }

    public void setGradedTime(LocalDate gradedTime) {
        this.gradedTime = gradedTime;
    }

    public Long getLearningPlanId() {
        return learningPlanId;
    }

    public void setLearningPlanId(Long learningPlanId) {
        this.learningPlanId = learningPlanId;
    }

    public LocalDate getEnrolTime() {
        return enrolTime;
    }

    public void setEnrolTime(LocalDate enrolTime) {
        this.enrolTime = enrolTime;
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", isActivated=" + isActivated +
                ", activationTime=" + activationTime +
                ", gradedTime=" + gradedTime +
                ", learningPlanId=" + learningPlanId +
                '}';
    }
}
