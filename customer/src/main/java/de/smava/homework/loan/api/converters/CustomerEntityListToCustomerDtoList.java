package de.smava.homework.loan.api.converters;

import de.smava.homework.loan.api.dto.CustomerDto;
import de.smava.homework.loan.persistence.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerEntityListToCustomerDtoList implements Converter<List<CustomerEntity>, List<CustomerDto>> {
    private final CustomerEntityToCustomerDto customerEntityToCustomerDto;

    @Override
    public List<CustomerDto> convert(List<CustomerEntity> customerEntityList) {
        return customerEntityList.stream()
                .map(customerEntityToCustomerDto::convert)
                .collect(Collectors.toList());
    }
}
