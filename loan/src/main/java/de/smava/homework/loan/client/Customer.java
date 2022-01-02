package de.smava.homework.loan.client;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
