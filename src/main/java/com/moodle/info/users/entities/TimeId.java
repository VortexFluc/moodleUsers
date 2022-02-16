package com.moodle.info.users.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TimeId implements Serializable {
    private Long userForPK;
    private Long courseId;

    public TimeId (Long id, Long courseId) {
        this.userForPK = id;
        this.courseId = courseId;
    }
}
