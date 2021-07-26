package com.demo.demo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.demo.demo.rest.model.Customer;
import com.demo.demo.rest.model.EnrollmentResult;
import com.demo.demo.rest.model.Insurance;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Enrollment class for handling the input format of a given CSV file
 * 
 * @author Henry Ton
 * @since 0.0.1
 */
@Service
public class EnrollmentService {
    @Autowired
    ObjectMapper objectMapper;

    /**
     * Formats the contents of a CSV file and separates customers based on
     * the type of insurance they have. For repeated entries with the same customer,
     * use the highest version available. 
     * 
     * The following format of the CSV flle should look like this (excluding the header row):
     * 
     *      |         id | first & last |      version |     insurance |
     *      |      uuid1 | John Fred    |            1 |         Geico |
     *      |      uuid2 | Micky Mouse  |            1 |      Allstate |
     *      |      uuid3 | Rick Carter  |            4 |         Geico |
     * 
     * 
     * @param file List<List<String>> file object that represents the structure of theimported CSV file
     * @return List<List<Customer>> sorted, where each sub list contains 
     *         a list of sorted Customer objects that have the same insurance
     */
    public List<EnrollmentResult> enroll(List<List<String>> file) throws Exception {
        if (file == null) {
            throw new IllegalArgumentException("Enrollment file is required.");
        }

        HashMap<String, Insurance> map = new HashMap<>();

        // row format - id, first & last, version, insurance
        for (List<String> row : file) {
            if (row == null || row.size() != 4) {
                throw new Exception("Enrollment file contains incorrect format.");
            }

            Insurance insurance = map.get(row.get(3));
            if (insurance == null) {
                map.put(row.get(3), new Insurance(row));
                insurance = map.get(row.get(3));
            }

            HashMap<String, Customer> customersMap = insurance.getCustomers();
            Customer customer = customersMap.get(row.get(0));
            if (customer != null) {
                int ver = Integer.parseInt(row.get(2));
                customer.setVersion((ver > customer.getVersion())? ver: customer.getVersion());
            } else {
                customersMap.put(row.get(0), new Customer(row));
            }

            map.put(row.get(3), insurance);
        }

        return map.values()
            .stream()
            .map(m -> new EnrollmentResult(m))
            .collect(Collectors.toList());
    }
}
