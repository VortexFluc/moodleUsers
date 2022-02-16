package com.moodle.info.users.models;

import com.moodle.info.users.entities.LearningPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearningPlanModel {
    private Long lpId;
    private String lpName;

    public static LearningPlanModel toModel(LearningPlan learningPlan) {
        LearningPlanModel model = new LearningPlanModel();
        model.setLpId(learningPlan.getLpId());
        model.setLpName(learningPlan.getLpName());
        return model;
    }
}
