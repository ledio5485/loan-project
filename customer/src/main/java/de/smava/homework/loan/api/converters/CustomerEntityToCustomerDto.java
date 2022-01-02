package de.smava.homework.loan.api.converters;

import de.smava.homework.loan.api.dto.CustomerDto;
import de.smava.homework.loan.persistence.CustomerEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityToCustomerDto implements Converter<CustomerEntity, CustomerDto> {

    @Override
    public CustomerDto convert(CustomerEntity customerEntity) {
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customerEntity, customerDto);

        return customerDto;
    }
}
