package com.demo.demo.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User entity
 * 
 * @author Henry Ton
 * @since 0.0.1
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    private Integer npiNumber;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String email;
    private String address1;
    private String address2;
    private String city;
    @Enumerated(EnumType.STRING)
    private State state;
    private String zipCode;
    @Enumerated(EnumType.STRING)
    private Country country;

    public enum Country {
        USA("United States");

        public final String name;

        private Country(String name) {
            this.name = name;
        }
    } 

    public enum State {
        FL("Florida");

        public final String name;
        private State(String name) {
            this.name = name;
        }
    }
}
