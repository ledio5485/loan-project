package de.smava.homework.loan.api.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
