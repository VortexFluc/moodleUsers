package com.moodle.info.users.repositories;

import com.moodle.info.users.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Integer> {
}
