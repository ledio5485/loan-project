package de.smava.homework.loan.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@FeignClient(name = "customer")
public interface CustomerClient {

    @GetMapping("/api/customers/{customerId}")
    Customer retrieveCustomer(@PathVariable("customerId") Long customerId);

    @GetMapping("/api/customers")
    Collection<Customer> retrieveCustomers(@RequestParam("id") Collection<Long> customerIds);
}
