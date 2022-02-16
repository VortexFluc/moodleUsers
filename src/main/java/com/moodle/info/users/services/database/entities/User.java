package com.moodle.info.users.services.database.entities;

public class User {
    private Long userId;
    private Long institutionId;
    private Long regionId;
    private Long cityId;

    public User(Long userId, Long institutionId, Long regionId, Long cityId) {
        this.userId = userId;
        this.institutionId = institutionId;
        this.regionId = regionId;
        this.cityId = cityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", institutionId=" + institutionId +
                ", regionId=" + regionId +
                ", cityId=" + cityId +
                '}';
    }
}
