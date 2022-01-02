package de.smava.homework.loan.service;

import de.smava.homework.loan.persistence.CustomerEntity;
import de.smava.homework.loan.persistence.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerEntity createCustomer(CustomerEntity newCustomer) {
        log.info("Creating the new customer {}", newCustomer);
        CustomerEntity createdCustomer = customerRepository.save(newCustomer);

        log.info("The new customer {} was stored successfully.", createdCustomer);
        return createdCustomer;
    }

    public Collection<CustomerEntity> getCustomers(Set<Long> ids) {
        return customerRepository.findAllById(ids);
    }

    public Optional<CustomerEntity> getCustomer(Long customerId) {
        log.info("Searching for the customer {}", customerId);
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);

        customer.ifPresent((val) -> log.info("Found the customer {}", val));
        return customer;
    }
}
