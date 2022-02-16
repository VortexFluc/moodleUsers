package com.moodle.info.users.services.database;

import com.moodle.info.users.services.database.entities.*;
import com.moodle.info.users.services.database.entities.Time;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class DataToSend {
    public static void sendCourses(Connection database, List<Course> courses) throws SQLException {
        String putQuery = "INSERT INTO courses (course_id, course_name, course_shortname, start_time) VALUES (?, ?, ?, ?)" +
                "ON CONFLICT (course_id) DO UPDATE SET course_name = EXCLUDED.course_name, course_shortname = EXCLUDED.course_shortname, " +
                "start_time = EXCLUDED.start_time";
        PreparedStatement preparedQuerry = database.prepareStatement(putQuery);
        for (Course course : courses) {
            preparedQuerry.setLong(1, course.getCourseId());
            preparedQuerry.setString(2, course.getCourseName());
            preparedQuerry.setString(3, course.getCourseShortname());
            if (course.getStartTime().equals(LocalDate.of(1970, 1, 1))) {
                preparedQuerry.setNull(4, Types.DATE);
            } else {
                preparedQuerry.setDate(4, Date.valueOf(course.getStartTime()));
            }
            preparedQuerry.executeUpdate();
        }
    }

    public static void sendLearningPlans(Connection database, List<LearningPlan> learningPlans) throws SQLException {
        String putQuery = "INSERT INTO learning_plan (lp_id, lp_name) VALUES (?, ?) ON CONFLICT (lp_id) DO UPDATE " +
                "SET lp_name = EXCLUDED.lp_name";
        PreparedStatement preparedQuery = database.prepareStatement(putQuery);
        for (LearningPlan learningPlan : learningPlans) {
            preparedQuery.setLong(1, learningPlan.getLpId());
            preparedQuery.setString(2, learningPlan.getLpName());
            preparedQuery.executeUpdate();
        }
    }

    public static void sendInstitutions(Connection database, List<Institution> institutions) throws SQLException {
        String putQuery = "INSERT INTO institutions (name) VALUES (?) ON CONFLICT DO NOTHING";
        PreparedStatement preparedQuery = database.prepareStatement(putQuery);
        for (Institution institution : institutions) {
            preparedQuery.setString(1, institution.getName());
            preparedQuery.executeUpdate();
        }
    }

    public static void sendUsers(Connection database, List<User> users) throws SQLException {
        String putQuery = "INSERT INTO users (city_id, institution_id, region_id, user_id) VALUES (?, ?, ?, ?) ON CONFLICT " +
                "(user_id) DO UPDATE SET city_id = EXCLUDED.city_id, institution_id = EXCLUDED.institution_id, region_id =" +
                " EXCLUDED.region_id";
        PreparedStatement preparedQuery = database.prepareStatement(putQuery);
        for (User user : users) {
            preparedQuery.setLong(1, user.getCityId());
            preparedQuery.setLong(2, user.getInstitutionId());
            preparedQuery.setLong(3, user.getRegionId());
            preparedQuery.setLong(4, user.getUserId());
            preparedQuery.executeUpdate();
        }
    }

    public static void sendTimes(Connection database, List<Time> times) throws SQLException {
        String putQuery = "INSERT INTO times (user_for_PK, course_id, enrol_time, activation_time, graded_time, is_activated, learning_plan_id, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, (SELECT id FROM users WHERE user_id = ?)) ON CONFLICT (user_for_PK, course_id) DO UPDATE SET " +
                "enrol_time = EXCLUDED.enrol_time, graded_time = EXCLUDED.graded_time, is_activated = EXCLUDED.is_activated, " +
                "learning_plan_id = EXCLUDED.learning_plan_id, user_id = (SELECT id FROM users WHERE user_id = ?)";

        String getQuery = "SELECT activation_time FROM times WHERE user_for_PK = ? AND course_id = ?";

        String putActivationTimeQuery = "UPDATE times SET activation_time = ? WHERE user_for_PK = ? AND course_id = ?";
        PreparedStatement preparedPutQuery = database.prepareStatement(putQuery);
        PreparedStatement preparedGetQuery = database.prepareStatement(getQuery);
        PreparedStatement preparedActTimeQuery = database.prepareStatement(putActivationTimeQuery);
        Set<Long> ids = new HashSet<>();
        for (Time time : times) {
            ids.add(time.getId());
            preparedPutQuery.setLong(1, time.getId());
            preparedPutQuery.setLong(2, time.getCourseId());

            preparedGetQuery.setLong(1, time.getId());
            preparedGetQuery.setLong(2, time.getCourseId());

            ResultSet getQueryAnswer = preparedGetQuery.executeQuery();
            if (getQueryAnswer.next()) {
                if (Objects.isNull(getQueryAnswer.getObject(1)) && !(time.getActivationTime().equals(LocalDate.of(1970, 1, 1)))) {
                    preparedActTimeQuery.setDate(1, Date.valueOf(time.getActivationTime()));
                    preparedActTimeQuery.setLong(2, time.getId());
                    preparedActTimeQuery.setLong(3, time.getCourseId());
                    preparedActTimeQuery.executeUpdate();
                }
            }

            if (time.getEnrolTime().equals(LocalDate.of(1970, 1, 1))) {
                preparedPutQuery.setNull(3, Types.DATE);
            } else {
                preparedPutQuery.setDate(3, Date.valueOf(time.getEnrolTime()));
            }
            if (time.getActivationTime().equals(LocalDate.of(1970, 1, 1))) {
                preparedPutQuery.setNull(4, Types.DATE);
            } else {
                preparedPutQuery.setDate(4, Date.valueOf(time.getActivationTime()));
            }
            if (time.getGradedTime().equals(LocalDate.of(1970, 1, 1))) {
                preparedPutQuery.setNull(5, Types.DATE);
            } else {
                preparedPutQuery.setDate(5, Date.valueOf(time.getGradedTime()));
            }
            preparedPutQuery.setBoolean(6, time.isActivated());
            preparedPutQuery.setLong(7, time.getLearningPlanId());
            preparedPutQuery.setLong(8, time.getId());
            preparedPutQuery.setLong(9, time.getId());
            preparedPutQuery.executeUpdate();
        }

        StringBuilder deleteInvalidCourses = new StringBuilder("DELETE FROM times WHERE user_for_pk NOT IN (");
        boolean is_first = true;
        for (Long id: ids) {
            if (!is_first) {
                deleteInvalidCourses.append(",");
            } else {
                is_first = false;
            }
            deleteInvalidCourses.append(id);
        }
        deleteInvalidCourses.append(")");
        PreparedStatement prepStatDelete = database.prepareStatement(deleteInvalidCourses.toString());
        prepStatDelete.executeUpdate();
    }
}
