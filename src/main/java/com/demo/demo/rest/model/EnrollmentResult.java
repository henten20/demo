package com.demo.demo.rest.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResult {
    private List<Customer> customers;
    private String insurance;
    public EnrollmentResult(Insurance insurance) {
        this.customers = new ArrayList<>(insurance.getCustomers().values());
        Collections.sort(customers, 
            (a, b) -> a.getFirstAndLastName().compareToIgnoreCase(b.getFirstAndLastName()));
        this.insurance = insurance.getInsurance();
    }
}
