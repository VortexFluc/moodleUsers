package com.moodle.info.users.repositories;

import com.moodle.info.users.entities.Time;
import com.moodle.info.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query ("SELECT u FROM User u INNER JOIN Time t ON u.id = t.user WHERE t.activationTime > ?1 OR t.gradedTime > ?1")
    List<User> findByPeriod(LocalDate date);

}
