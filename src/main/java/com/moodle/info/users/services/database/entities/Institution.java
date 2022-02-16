package com.moodle.info.users.services.database.entities;

public class Institution {
    private String name;

    public Institution(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Institution{" +
                "name='" + name + '\'' +
                '}';
    }
}
