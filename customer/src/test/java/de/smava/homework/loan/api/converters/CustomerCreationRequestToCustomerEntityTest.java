package de.smava.homework.loan.api.converters;

import de.smava.homework.loan.api.dto.CustomerCreationRequest;
import de.smava.homework.loan.persistence.CustomerEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CustomerCreationRequestToCustomerEntityTest {

    private final CustomerCreationRequestToCustomerEntity sut = new CustomerCreationRequestToCustomerEntity();

    @Test
    void shouldConvertCorrectly() {
        CustomerCreationRequest customerCreationRequest = new CustomerCreationRequest();
        customerCreationRequest.setUserId(1L);
        customerCreationRequest.setFirstName("first name");
        customerCreationRequest.setLastName("last name");
        customerCreationRequest.setEmail("email");
        customerCreationRequest.setPhone("phone number");

        CustomerEntity actual = sut.convert(customerCreationRequest);

        CustomerEntity expected = new CustomerEntity();
        expected.setUserId(customerCreationRequest.getUserId());
        expected.setFirstName(customerCreationRequest.getFirstName());
        expected.setLastName(customerCreationRequest.getLastName());
        expected.setEmail(customerCreationRequest.getEmail());
        expected.setPhone(customerCreationRequest.getPhone());
        assertThat(actual).isEqualTo(expected);
    }
}