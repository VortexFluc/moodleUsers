package com.moodle.info.users.models;

import com.moodle.info.users.entities.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeModel {
    private Long courseId;
    private boolean isActivated;
    private LocalDate activationTime;
    private LocalDate gradedTime;
    private Long learningPlanId;

    public static TimeModel toModel(Time timeEntity) {
        TimeModel model = new TimeModel();
        model.setActivated(timeEntity.isActivated());
        model.setActivationTime(timeEntity.getActivationTime());
        model.setGradedTime(timeEntity.getGradedTime());
        model.setCourseId(timeEntity.getCourseId());
        model.setLearningPlanId(timeEntity.getLearningPlanId());
        return model;
    }
}
