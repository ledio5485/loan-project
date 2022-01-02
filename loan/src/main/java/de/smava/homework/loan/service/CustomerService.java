package de.smava.homework.loan.service;

import de.smava.homework.loan.client.Customer;
import de.smava.homework.loan.client.CustomerClient;
import de.smava.homework.loan.client.CustomerNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@CacheConfig(cacheNames={"customers"})
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerClient customerClient;

    @Cacheable
    public Customer retrieveCustomer(Long customerId) {
        log.info("Retrieving customer {}", customerId);
        try {
            Customer customer = customerClient.retrieveCustomer(customerId);
            log.info("Found customer {}", customer);
            return customer;
        } catch (Exception exception) {
            log.error("There was an error while getting the customer {}, {} ", customerId, exception);
            if (exception instanceof FeignException && ((FeignException)exception).status() == HttpStatus.NOT_FOUND.value()) {
                throw new CustomerNotFoundException();
            }
            throw new RuntimeException(String.format("There was an error while getting the customer %d", customerId));
        }
    }

    @Cacheable
    public Collection<Customer> retrieveCustomers(Collection<Long> customerIds) {
        log.info("Retrieving the information of the following customers {}", customerIds);
        return customerClient.retrieveCustomers(customerIds);
    }
}
