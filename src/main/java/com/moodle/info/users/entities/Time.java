package com.moodle.info.users.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "times")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(TimeId.class)
public class Time {
    @Id
    @Column(name = "user_for_PK") private Long userForPK;
    @Id
    @Column(name = "course_id") private Long courseId;
    @Column(name = "is_activated") private boolean isActivated;
    @Column(name = "enrol_time") private LocalDate enrolTime;
    @Column(name = "activation_time") private LocalDate activationTime;
    @Column(name = "graded_time") private LocalDate gradedTime;
    @Column(name = "learning_plan_id") private Long learningPlanId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
