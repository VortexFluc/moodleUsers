package com.moodle.info.users.repositories;

import com.moodle.info.users.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
