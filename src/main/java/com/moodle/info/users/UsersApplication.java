package com.moodle.info.users;

import com.moodle.info.users.services.database.ParseExcel;
import com.moodle.info.users.services.database.PopulateDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
@EnableScheduling
public class UsersApplication {
	@Scheduled(cron = "0 0/30 * * * *")
	public static void updateDB() {
		try {
			System.out.println("Try to update DB...");
			PopulateDB.actulizeDB();
			System.out.println("UPDATE SUCCESS!");
		} catch (Exception e) {
			System.out.println("UPDATE FAILED! ERROR: " + e.getMessage());
		}
	}

	public static void main(String[] args) throws SQLException, IOException {
		SpringApplication.run(UsersApplication.class, args);
		//ParseExcel.parse("/usr/Справочник_регионов_и_городов.xlsx");
		ParseExcel.parse("C:\\Users\\drdoo\\Desktop\\Справочник_регионов_и_городов.xlsx");
		updateDB();
	}
}
