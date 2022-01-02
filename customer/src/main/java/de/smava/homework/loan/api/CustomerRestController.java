package de.smava.homework.loan.api;

import de.smava.homework.loan.api.dto.CustomerCreationRequest;
import de.smava.homework.loan.api.dto.CustomerCreationResponse;
import de.smava.homework.loan.api.dto.CustomerDto;
import de.smava.homework.loan.persistence.CustomerEntity;
import de.smava.homework.loan.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
public class CustomerRestController implements CustomerResource {
    private final CustomerService customerService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<CustomerCreationResponse> createCustomer(@Valid CustomerCreationRequest customerCreationRequest) {
        CustomerEntity newCustomer = customerService.createCustomer(conversionService.convert(customerCreationRequest, CustomerEntity.class));
        CustomerCreationResponse customerCreationResponse = new CustomerCreationResponse();
        customerCreationResponse.setId(newCustomer.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(customerCreationResponse);
    }

    @Override
    public ResponseEntity<Collection<CustomerDto>> retrieveCustomersByIds(Set<Long> ids) {
        List<CustomerDto> customers = (List<CustomerDto>) conversionService.convert(customerService.getCustomers(ids),
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(CustomerEntity.class)),
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(CustomerDto.class)));
        return ResponseEntity.ok(customers);
    }

    @Override
    public ResponseEntity<CustomerDto> retrieveCustomerById(Long id) {
        Optional<CustomerDto> customerDto = customerService.getCustomer(id)
                .map(customerEntityToDto());

        return ResponseEntity.of(customerDto);
    }

    private Function<CustomerEntity, CustomerDto> customerEntityToDto() {
        return customerEntity -> conversionService.convert(customerEntity, CustomerDto.class);
    }
}
