package com.moodle.info.users.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "learning_plan")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearningPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (unique = true) private Long lpId;
    private String lpName;
}
