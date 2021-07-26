package com.demo.demo.rest.model;

import java.util.HashMap;
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
public class Insurance {
    private String insurance;
    private HashMap<String, Customer> customers;
    public Insurance(List<String> row) {
        this.insurance = row.get(3);
        this.customers = new HashMap<>();
    }
}