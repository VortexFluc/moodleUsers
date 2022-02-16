package com.moodle.info.users.models;

import com.moodle.info.users.entities.Time;
import com.moodle.info.users.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long userId;
    private Long institutionId;
    private Long regionId;
    private Long cityId;
    private List<TimeModel> courses;

    public static UserModel toModel(User userEntity) {
        UserModel model = new UserModel();
        model.setUserId(userEntity.getUserId());
        model.setInstitutionId(userEntity.getInstitutionId());
        model.setRegionId(userEntity.getRegionId());
        model.setCityId(userEntity.getCityId());
        model.setCourses(userEntity.getCourses().stream().map(TimeModel::toModel).collect(Collectors.toList()));
        return model;
    }
}
