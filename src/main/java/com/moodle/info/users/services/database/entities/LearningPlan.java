package com.moodle.info.users.services.database.entities;

public class LearningPlan {
    private Long lpId;
    private String lpName;

    public LearningPlan (Long id, String name) {
        lpId = id;
        lpName = name;
    }

    public Long getLpId() {
        return lpId;
    }

    public void setLpId(Long lpId) {
        this.lpId = lpId;
    }

    public String getLpName() {
        return lpName;
    }

    public void setLpName(String lpName) {
        this.lpName = lpName;
    }

    @Override
    public String toString() {
        return "ID: " + lpId + ", Name: " + lpName;
    }
}
