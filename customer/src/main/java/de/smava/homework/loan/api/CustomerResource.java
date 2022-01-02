package de.smava.homework.loan.api;

import de.smava.homework.loan.api.dto.CustomerCreationRequest;
import de.smava.homework.loan.api.dto.CustomerCreationResponse;
import de.smava.homework.loan.api.dto.CustomerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RequestMapping("/api/customers")
public interface CustomerResource {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    ResponseEntity<CustomerCreationResponse> createCustomer(@RequestBody @Valid CustomerCreationRequest customerCreationRequest);

    @GetMapping
    @ResponseBody
    ResponseEntity<Collection<CustomerDto>> retrieveCustomersByIds(@RequestParam("id") Set<Long> ids);

    @GetMapping("/{id}")
    @ResponseBody
    ResponseEntity<CustomerDto> retrieveCustomerById(@PathVariable("id") Long id);
}
