package com.demo.demo.rest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.demo.demo.model.User;
import com.demo.demo.repository.UserRepository;
import com.demo.demo.rest.model.EnrollmentResult;
import com.demo.demo.rest.model.UserInput;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * Demo controller
 * 
 * @author Henry Ton
 * @since 0.0.1
 */
@Slf4j
@RestController
@EnableAutoConfiguration
public class DemoController {
    private final LispParserService lispParserService;
    private final EnrollmentService enrollmentService;
    private final UserRepository userRepository;

    public DemoController(EnrollmentService enrollmentService, LispParserService lispParser, UserRepository userRepository) {
        this.lispParserService = lispParser;
        this.enrollmentService = enrollmentService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserInput userInput) throws Exception {
        Optional<User> user = userRepository.findByEmail(userInput.getEmail());
        if (user.isPresent()) {
            // handle error
            log.error("User already exists!");
            return null;
        }

        User newUser = User.builder()
            .npiNumber(userInput.getNpiNumber())
            .firstName(userInput.getFirstName())
            .lastName(userInput.getLastName())
            .telephoneNumber(userInput.getTelephoneNumber())
            .email(userInput.getEmail())
            .address1(userInput.getAddress1())
            .address2(userInput.getAddress2())
            .city(userInput.getCity())
            .state(User.State.valueOf(userInput.getState()))
            .zipCode(userInput.getZipCode())
            .country(User.Country.valueOf(userInput.getCountry()))
            .build();

        return userRepository.save(newUser);
    }

    @GetMapping("/user")
    public User getUser(@RequestParam UUID id) throws Exception {
        return userRepository.findById(id).get();
    }

    @PostMapping("/enroll")
    public List<EnrollmentResult> enroll(@RequestBody List<List<String>> csvFile) throws Exception, JsonProcessingException {
        List<EnrollmentResult> result = enrollmentService.enroll(csvFile);
        Collections.sort(result, 
            (a, b) -> a.getInsurance().compareToIgnoreCase(b.getInsurance()));
         return result;
    }

    @PostMapping("/parse")
    public boolean parse(@RequestBody String lisp) throws Exception {
        return lispParserService.parseLisp(lisp);
    }
}