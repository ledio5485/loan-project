package de.smava.homework.loan.api.converters;

import de.smava.homework.loan.api.dto.CustomerCreationRequest;
import de.smava.homework.loan.persistence.CustomerEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreationRequestToCustomerEntity implements Converter<CustomerCreationRequest, CustomerEntity> {

    @Override
    public CustomerEntity convert(CustomerCreationRequest customerCreationRequest) {
        CustomerEntity customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(customerCreationRequest, customerEntity);

        return customerEntity;
    }
}
