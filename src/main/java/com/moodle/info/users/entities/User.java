package com.moodle.info.users.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    // For local database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", unique = true) private Long userId;
    @Column(name = "institution_id") private Long institutionId;
    @Column(name = "region_id") private Long regionId;
    @Column(name = "city_id") private Long cityId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Time> courses;
}
