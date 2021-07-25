package com.demo.demo.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

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
    private HashMap<String, Insurance> map = new HashMap<>();

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
     * @return List<List<Customer>> object where each sub list contains a list of Customer objects
     *         that have the same insurance
     */
    public List<Result> enroll(List<List<String>> file) {
        if (file == null) {
            throw new IllegalArgumentException();
        }

        // row format - id, first & last, version, insurance
        for(List<String> row : file) {
            if (row == null || row.size() != 4) {
                break;
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
            .map(m -> new Result(m))
            .collect(Collectors.toList());
    }

    @Getter
    @Setter
    public class Result {
        private List<Customer> customers;
        private String insurance;
        public Result(Insurance insurance) {
            this.customers = new ArrayList<>(insurance.getCustomers().values());
            this.insurance = insurance.getInsurance();
        }
    }

    @Getter
    @Setter
    public class Insurance {
        private String insurance;
        private HashMap<String, Customer> customers;
        public Insurance(List<String> row) {
            this.insurance = row.get(3);
            this.customers = new HashMap<>();
        }
    }

    @Getter
    @Setter
    public class Customer {
        private String id;
        private int version;
        private String firstAndLastName;
        private String insurance;
        public Customer(List<String> customerInfo) {
            this.id = customerInfo.get(0);
            this.firstAndLastName = customerInfo.get(1);
            this.version = Integer.parseInt(customerInfo.get(2));
            this.insurance = customerInfo.get(3);
        }
    }
}
