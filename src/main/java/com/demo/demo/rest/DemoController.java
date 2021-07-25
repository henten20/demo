package com.demo.demo.rest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import com.demo.demo.LispParser;
import com.demo.demo.model.User;
import com.demo.demo.repository.UserRepository;
import com.demo.demo.rest.EnrollmentService.Customer;
import com.demo.demo.rest.EnrollmentService.Insurance;
import com.demo.demo.rest.EnrollmentService.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@EnableAutoConfiguration
public class DemoController {
    private final LispParser lispParser;
    private final EnrollmentService enrollmentService;
    private final UserRepository userRepository;

    public DemoController(EnrollmentService enrollmentService, LispParser lispParser, UserRepository userRepository) {
        this.lispParser = lispParser;
        this.enrollmentService = enrollmentService;
        this.userRepository = userRepository;
    }

    @RequestMapping("/")
    public ModelAndView home(Model model) {
        return new ModelAndView("index.html");
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
    public User getUser(@RequestParam UUID id) {
        return userRepository.findById(id).get();
    }

    @PostMapping("/enroll")
    public List<Result> enroll(@RequestBody List<List<String>> csvFile) throws JsonProcessingException {
        return enrollmentService.enroll(csvFile);
    }

    @PostMapping("/parse")
    public boolean parse(@RequestBody String lisp) {
        return lispParser.parseLisp(lisp);
    }
}