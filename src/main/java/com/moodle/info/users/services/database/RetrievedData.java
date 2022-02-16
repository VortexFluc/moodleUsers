package com.moodle.info.users.services.database;

import com.moodle.info.users.services.database.entities.Time;
import com.moodle.info.users.services.database.entities.*;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RetrievedData {
    public static List<Course> grepCourses (Connection database) throws SQLException {
        String grepQuery = "SELECT id, fullname, shortname, startdate FROM mdl_course";
        PreparedStatement prepQuery = database.prepareStatement(grepQuery);
        ResultSet answer = prepQuery.executeQuery();
        List<Course> courses = new ArrayList<>();
        while (answer.next()) {
            int id;
            Timestamp timestamp;
            String fullname, shortname;
            id = answer.getInt(1);
            if (id == 1) {
                continue;
            }
            fullname = answer.getString(2).trim();
            shortname = answer.getString(3).trim();
            timestamp = Timestamp.from(Instant.ofEpochSecond(answer.getBigDecimal(4).longValue()));
            Course course = new Course((long)id, fullname, shortname, timestamp.toLocalDateTime().toLocalDate());
            courses.add(course);
        }
        return courses;
    }

    public static List<LearningPlan> grepLearningPlans (Connection database) throws SQLException {
        String grepQuery = "SELECT id, name from mdl_cohort;";
        PreparedStatement prepQuery = database.prepareStatement(grepQuery);
        ResultSet answer = prepQuery.executeQuery();
        List<LearningPlan> learningPlans = new ArrayList<>();
        while (answer.next()) {
            int id;
            String learningPlanName;
            id = answer.getInt(1);
            learningPlanName = answer.getString(2);
            LearningPlan learningPlan = new LearningPlan((long)id, learningPlanName);
            learningPlans.add(learningPlan);
        }
        return learningPlans;
    }

    public static List<Institution> grepInstitution(Connection database) throws SQLException {
        List<Institution> institutions = new ArrayList<>();
        String getInstitutions = "SELECT param1 from mdl_user_info_field where name = 'Учреждение'";
        PreparedStatement getInstPS = database.prepareStatement(getInstitutions);
        ResultSet answer = getInstPS.executeQuery();
        answer.next();
        for (String s : answer.getString(1).split("\n")) {
            Institution inst = new Institution(s);
            institutions.add(inst);
        }
        return institutions;
    }

    public static List<Time> grepTime(Connection mysql, Connection postgres) throws SQLException {
        List<Long> ids = getUsersIds(mysql);
        List<Time> times = new ArrayList<>();
        for (Long id: ids) {
            String getEnrolId = "SELECT enrolid, timecreated FROM mdl_user_enrolments WHERE userid = ?";
            PreparedStatement getEnIdStatement = mysql.prepareStatement(getEnrolId);
            getEnIdStatement.setLong(1, id);
            ResultSet EnrolIdAnswer = getEnIdStatement.executeQuery();
            while (EnrolIdAnswer.next()) {
                String lp_name;
                Long enrolId = EnrolIdAnswer.getLong(1);
                Timestamp enrolTimeStamp = Timestamp.from(Instant.ofEpochSecond(EnrolIdAnswer.getBigDecimal(2).longValue()));
                LocalDate enrolLocalDate = enrolTimeStamp.toLocalDateTime().toLocalDate();
                String getCourseId = "SELECT courseid, name FROM mdl_enrol WHERE id = " + enrolId;
                PreparedStatement prepStatGetCourseId = mysql.prepareStatement(getCourseId);
                ResultSet CourseIdAnswer = prepStatGetCourseId.executeQuery();
                CourseIdAnswer.next();
                Long courseId = CourseIdAnswer.getLong(1);
                if (!Objects.isNull(CourseIdAnswer.getObject(2))) {
                    lp_name = CourseIdAnswer.getString(2);
                } else {
                    lp_name = "";
                }

                String getActivationTime = "SELECT timeaccess FROM mdl_user_lastaccess WHERE userid = ? AND courseid = ?";
                PreparedStatement prepStatActTime = mysql.prepareStatement(getActivationTime);
                prepStatActTime.setLong(1, id);
                prepStatActTime.setLong(2, courseId);
                ResultSet actTimeAnswer = prepStatActTime.executeQuery();
                Timestamp activationTimestamp;
                LocalDate activationLocalDate;
                if (actTimeAnswer.next()) {
                    activationTimestamp = Timestamp.from(Instant.ofEpochSecond(actTimeAnswer.getBigDecimal(1).longValue()));
                    activationLocalDate = Timestamp.from(Instant.ofEpochSecond(actTimeAnswer.getBigDecimal(1).longValue())).toLocalDateTime().toLocalDate();
                } else {
                    activationTimestamp = Timestamp.from(Instant.ofEpochSecond(0L));
                    activationLocalDate = Timestamp.from(Instant.ofEpochSecond(0L)).toLocalDateTime().toLocalDate();
                }

                String getCompleteTime = "SELECT timecompleted FROM mdl_course_completions WHERE course = ? AND userid = ?";
                PreparedStatement prepStatCompleteTime = mysql.prepareStatement(getCompleteTime);
                prepStatCompleteTime.setLong(1, courseId);
                prepStatCompleteTime.setLong(2, id);
                ResultSet completeTimeAnswer = prepStatCompleteTime.executeQuery();
                Timestamp completeTimestamp;
                LocalDate completeLocalDate;
                if (completeTimeAnswer.next()) {
                    try {
                        completeTimestamp = Timestamp.from(Instant.ofEpochSecond(completeTimeAnswer.getBigDecimal(1).longValue()));
                        completeLocalDate = completeTimestamp.toLocalDateTime().toLocalDate();
                    } catch (NullPointerException npe) {
                        completeTimestamp = Timestamp.from(Instant.ofEpochSecond(0));
                        completeLocalDate = completeTimestamp.toLocalDateTime().toLocalDate();
                    }
                } else {
                    completeTimestamp = Timestamp.from(Instant.ofEpochSecond(0));
                    completeLocalDate = completeTimestamp.toLocalDateTime().toLocalDate();
                }
                Long learningPlanId;
                if (!lp_name.equals("")) {
                    String getPlans = "SELECT lp_id FROM learning_plan WHERE lp_name = '" + lp_name + "'";
                    PreparedStatement prepStatPlans = postgres.prepareStatement(getPlans);
                    ResultSet plansAnswer = prepStatPlans.executeQuery();
                    if (plansAnswer.next()) {
                        learningPlanId = plansAnswer.getLong(1);
                    } else {
                        learningPlanId = 0L;
                    }
                } else {
                    learningPlanId = 0L;
                }
                boolean is_activated = !activationTimestamp.equals(Timestamp.from(Instant.ofEpochSecond(0))) && completeTimestamp.equals(Timestamp.from(Instant.ofEpochSecond(0)));
                Time time = new Time(id, courseId, is_activated, enrolLocalDate, activationLocalDate ,completeLocalDate, learningPlanId);
                times.add(time);
            }
        }
        return times;
    }

    public static List<User> grepUsers(Connection mysql, Connection postgres) throws SQLException {
        List<User> users = new ArrayList<>();
        List<Long> ids = getUsersIds(mysql);
        for (Long id : ids) {
            String getValRegionCity = "SELECT data FROM mdl_user_info_data WHERE userid = ? AND fieldid = 4";
            PreparedStatement prepStatRegCity = mysql.prepareStatement(getValRegionCity);
            prepStatRegCity.setLong(1, id);
            ResultSet RegionCityAnswer = prepStatRegCity.executeQuery();
            Long region_id, city_id;
            if (RegionCityAnswer.next()) {
                if (RegionCityAnswer.getString(1).equals("")) {
                    region_id = 0L;
                    city_id = 0L;
                } else {
                    String getIdRegionCity = "SELECT region_id, city_id FROM region_and_city WHERE region_city = '" + RegionCityAnswer.getString(1) + "'";
                    PreparedStatement prepStatRegCityId = postgres.prepareStatement(getIdRegionCity);
                    ResultSet RegCityIdAnswer = prepStatRegCityId.executeQuery();
                    RegCityIdAnswer.next();
                    region_id = RegCityIdAnswer.getLong(1);
                    city_id = RegCityIdAnswer.getLong(2);
                }
            } else {
                region_id = 0L;
                city_id = 0L;
            }

            String getValInstitution = "SELECT data FROM mdl_user_info_data WHERE userid = ? AND fieldid = 1";
            PreparedStatement prepStatInstitution = mysql.prepareStatement(getValInstitution);
            prepStatInstitution.setLong(1, id);
            ResultSet InstitutionAnswer = prepStatInstitution.executeQuery();
            Long inst_id;
            if (InstitutionAnswer.next()) {
                if (InstitutionAnswer.getString(1).equals("")) {
                    inst_id = 0L;
                } else {
                    String getIdInstitution = "SELECT id FROM institutions WHERE name = '" + InstitutionAnswer.getString(1) + "'";
                    PreparedStatement prepStatIdInst = postgres.prepareStatement(getIdInstitution);
                    ResultSet IdInstAnswer = prepStatIdInst.executeQuery();
                    if (IdInstAnswer.next()) {
                        inst_id = IdInstAnswer.getLong(1);
                    } else {
                        inst_id = 0L;
                    }
                }
            } else {
                inst_id = 0L;
            }
            User user = new User(id, inst_id, region_id, city_id);
            users.add(user);
        }
        return users;
    }

    private static List<Long> getUsersIds(Connection database) throws SQLException {
        String getQuery = "SELECT id FROM mdl_user WHERE deleted = 0";
        PreparedStatement preparedQuery = database.prepareStatement(getQuery);
        ResultSet answer = preparedQuery.executeQuery();
        List<Long> ids = new ArrayList<>();
        while (answer.next()) {
            ids.add(answer.getLong(1));
        }
        return ids;
    }
}
