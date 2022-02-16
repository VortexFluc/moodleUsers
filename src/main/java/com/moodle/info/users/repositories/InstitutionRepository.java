package com.moodle.info.users.repositories;

import com.moodle.info.users.entities.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {
}
