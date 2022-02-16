package com.moodle.info.users.repositories;

import com.moodle.info.users.entities.LearningPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningPlanRepository extends JpaRepository<LearningPlan, Long> {
}
