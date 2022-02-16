package com.moodle.info.users.controllers;

import com.moodle.info.users.entities.*;
import com.moodle.info.users.models.CourseModel;
import com.moodle.info.users.models.LearningPlanModel;
import com.moodle.info.users.models.UserModel;
import com.moodle.info.users.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class UserController {

    @Value("${token.value}")
    private String token;

    @Autowired
    private UserRepository userRepository;

    // Get all users
    @GetMapping("/getAll")
    @ResponseBody
    public ResponseEntity<Iterable<UserModel>> getAllUsers(@RequestHeader(value = "x-api-key", required = false) String token) {
        if (token == null || !token.equals(this.token)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(userRepository.findAll().stream().map(UserModel::toModel).collect(Collectors.toList()), HttpStatus.OK);
        }
    }

    // Get user in a period of "date" to current time
    @GetMapping("/getByPeriod")
    @ResponseBody
    public ResponseEntity<Iterable<UserModel>> getByPeriod(@RequestHeader(value = "x-api-key", required = false) String token, @RequestParam String date) {
        if (token == null || !token.equals(this.token)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            LocalDate lDate = LocalDate.parse(date);
            return new ResponseEntity<>(userRepository.findByPeriod(lDate).stream().map(UserModel::toModel).collect(Collectors.toList()), HttpStatus.OK);
        }
    }

    @Autowired
    RegionRepository regionRepository;

    // Get list of current names of regions
    @GetMapping("/getReg")
    @ResponseBody
    public ResponseEntity<Iterable<Region>> getRegions(@RequestHeader(value = "x-api-key", required = false) String token) {
        if (token == null || !token.equals(this.token)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(regionRepository.findAll(), HttpStatus.OK);
        }
    }

    @Autowired
    CityRepository cityRepository;

    @GetMapping("/getCity")
    @ResponseBody
    public ResponseEntity<Iterable<City>> getCity(@RequestHeader(value = "x-api-key", required = false) String token) {
        if (token == null || !token.equals(this.token)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(cityRepository.findAll(), HttpStatus.OK);
        }
    }

    @Autowired
    InstitutionRepository institutionRepository;

    // Get list of current names of institutions
    @GetMapping("/getInst")
    @ResponseBody
    public ResponseEntity<Iterable<Institution>> getInstitutions(@RequestHeader(value = "x-api-key", required = false) String token) {
        if (token == null || !token.equals(this.token)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(institutionRepository.findAll(), HttpStatus.OK);
        }
    }

    // Get list of current names of courses
    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/getCourses")
    @ResponseBody
    public ResponseEntity<Iterable<CourseModel>> getCourses(@RequestHeader(value = "x-api-key", required = false) String token) {
        if (token == null || !token.equals(this.token)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(courseRepository.findAll().stream().map(CourseModel::toModel).collect(Collectors.toList()), HttpStatus.OK);
        }
    }

    // Get list of current names of learning plans aka global groups
    @Autowired
    LearningPlanRepository learningPlanRepository;

    @GetMapping("/getPlans")
    @ResponseBody
    public ResponseEntity<Iterable<LearningPlanModel>> getPlans(@RequestHeader(value = "x-api-key", required = false) String token) {
        if (token == null || !token.equals(this.token)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(learningPlanRepository.findAll().stream().map(LearningPlanModel::toModel).collect(Collectors.toList()), HttpStatus.OK);
        }
    }
}
