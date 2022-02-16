package com.moodle.info.users.services.database;

import com.moodle.info.users.services.database.entities.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DataManager {
    public static void actulize(Connection mysql, Connection postgres) throws SQLException {
        makeTransaction(mysql, postgres);
        //putDataTo(postgres);
    }

    private static void makeTransaction(Connection mysql, Connection postgres) throws SQLException {
        List<Course> courses = RetrievedData.grepCourses(mysql);
        DataToSend.sendCourses(postgres, courses);
        List<LearningPlan> learningPlans = RetrievedData.grepLearningPlans(mysql);
        DataToSend.sendLearningPlans(postgres, learningPlans);
        List<Institution> institutions = RetrievedData.grepInstitution(mysql);
        DataToSend.sendInstitutions(postgres, institutions);
        List<User> users = RetrievedData.grepUsers(mysql, postgres);
        DataToSend.sendUsers(postgres, users);
        List<Time> times = RetrievedData.grepTime(mysql, postgres);
        DataToSend.sendTimes(postgres, times);
    }
}
