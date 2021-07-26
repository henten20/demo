package com.demo.demo.rest.model;

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