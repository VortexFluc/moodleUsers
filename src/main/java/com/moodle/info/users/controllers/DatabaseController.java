package com.moodle.info.users.controllers;

import com.moodle.info.users.services.database.PopulateDB;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping
public class DatabaseController {
    @GetMapping("/refreshDB")
    @ResponseBody
    public String refreshDB() {
        try {
            System.out.println("Base refreshed manually at: " + LocalDateTime.now());
            PopulateDB.actulizeDB();
            return "OK!";
        } catch (Exception e) {
            return "Error! Refreshing failed: " + e.getMessage();
        }
    }
}
