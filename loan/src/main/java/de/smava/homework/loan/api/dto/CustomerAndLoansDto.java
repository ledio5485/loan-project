package de.smava.homework.loan.api.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class CustomerAndLoansDto {
    private CustomerDto customer;
    private Collection<LoanDto> loans;
}
