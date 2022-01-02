package de.smava.homework.loan.service;

import de.smava.homework.loan.client.Customer;
import de.smava.homework.loan.persistence.LoanEntity;
import lombok.Data;

import java.util.Collection;

@Data
public class CustomerAndLoans {
    private Customer customer;
    private Collection<LoanEntity> loans;
}
