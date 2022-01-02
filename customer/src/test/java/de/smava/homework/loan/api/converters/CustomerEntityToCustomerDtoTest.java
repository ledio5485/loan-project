package de.smava.homework.loan.api.converters;

import de.smava.homework.loan.api.dto.CustomerDto;
import de.smava.homework.loan.persistence.CustomerEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CustomerEntityToCustomerDtoTest {
    private final CustomerEntityToCustomerDto sut = new CustomerEntityToCustomerDto();

    @Test
    void shouldConvertCorrectly() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setUserId(2L);
        customerEntity.setFirstName("first name");
        customerEntity.setLastName("last name");
        customerEntity.setEmail("email");
        customerEntity.setPhone("phone number");

        CustomerDto actual = sut.convert(customerEntity);

        CustomerDto expected = new CustomerDto();
        expected.setId(actual.getId());
        expected.setUserId(actual.getUserId());
        expected.setFirstName(actual.getFirstName());
        expected.setLastName(actual.getLastName());
        expected.setEmail(actual.getEmail());
        expected.setPhone(actual.getPhone());
        assertThat(actual).isEqualTo(expected);
    }
}