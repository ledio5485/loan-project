package de.smava.homework.loan.api.converters;

import de.smava.homework.loan.api.dto.CustomerAndLoansDto;
import de.smava.homework.loan.api.dto.CustomerDto;
import de.smava.homework.loan.service.CustomerAndLoans;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerAndLoansToCustomerAndLoansDto implements Converter<CustomerAndLoans, CustomerAndLoansDto> {

    @Override
    public CustomerAndLoansDto convert(CustomerAndLoans customerAndLoans) {
        CustomerAndLoansDto dto = new CustomerAndLoansDto();
        dto.setCustomer(new CustomerDto());
        BeanUtils.copyProperties(customerAndLoans.getCustomer(), dto.getCustomer());
        BeanUtils.copyProperties(customerAndLoans, dto);

        return dto;
    }
}
