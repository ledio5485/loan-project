package de.smava.homework.loan.api;

import de.smava.homework.loan.AbstractIntegrationTest;
import de.smava.homework.loan.api.dto.CustomerCreationRequest;
import de.smava.homework.loan.api.dto.CustomerCreationResponse;
import de.smava.homework.loan.api.dto.CustomerDto;
import de.smava.homework.loan.persistence.CustomerEntity;
import de.smava.homework.loan.persistence.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CustomerRestControllerIntegrationTest extends AbstractIntegrationTest {
    private static final String URL = "/api/customers";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository repository;

    @Test
    void shouldCreateNewCustomer() {
        CustomerCreationRequest customerCreationRequest = new CustomerCreationRequest();
        customerCreationRequest.setUserId(1L);
        customerCreationRequest.setFirstName("John");
        customerCreationRequest.setLastName("Doe");
        customerCreationRequest.setEmail("john@doe.test");
        customerCreationRequest.setPhone("+1 234 567 890");

        ResponseEntity<CustomerCreationResponse> response = restTemplate.postForEntity(URL, customerCreationRequest, CustomerCreationResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long id = Objects.requireNonNull(response.getBody()).getId();
        assertThat(id).isNotNull();

        CustomerEntity actual = repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Customer %d not found.", id)));

        CustomerEntity expected = new CustomerEntity();
        expected.setId(id);
        expected.setUserId(1L);
        expected.setFirstName("John");
        expected.setLastName("Doe");
        expected.setEmail("john@doe.test");
        expected.setPhone("+1 234 567 890");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldRetrieveMultipleCustomersByIds() {
        CustomerCreationRequest customerCreationRequest1 = new CustomerCreationRequest();
        customerCreationRequest1.setUserId(1L);
        customerCreationRequest1.setFirstName("John");
        customerCreationRequest1.setLastName("Doe");
        customerCreationRequest1.setEmail("john@doe.test");
        customerCreationRequest1.setPhone("+1 234 567 890");
        ResponseEntity<CustomerCreationResponse> response1 = restTemplate.postForEntity(URL, customerCreationRequest1, CustomerCreationResponse.class);

        Long id1 = Objects.requireNonNull(response1.getBody()).getId();

        CustomerCreationRequest customerCreationRequest2 = new CustomerCreationRequest();
        customerCreationRequest2.setUserId(2L);
        customerCreationRequest2.setFirstName("John");
        customerCreationRequest2.setLastName("Cena");
        customerCreationRequest2.setEmail("john@cena.test");
        customerCreationRequest2.setPhone("+1 234 567 890");
        ResponseEntity<CustomerCreationResponse> response2 = restTemplate.postForEntity(URL, customerCreationRequest2, CustomerCreationResponse.class);

        Long id2 = Objects.requireNonNull(response2.getBody()).getId();

        String ids = String.join(",", Arrays.asList(id1.toString(), id2.toString(), Long.toString(33)));
        ResponseEntity<CustomerDto[]> actual = restTemplate.getForEntity(String.format("%s?id=%s", URL, ids), CustomerDto[].class);

        CustomerDto expected1 = new CustomerDto();
        expected1.setId(id1);
        expected1.setUserId(1L);
        expected1.setFirstName("John");
        expected1.setLastName("Doe");
        expected1.setEmail("john@doe.test");
        expected1.setPhone("+1 234 567 890");
        CustomerDto expected2 = new CustomerDto();
        expected2.setId(id2);
        expected2.setUserId(2L);
        expected2.setFirstName("John");
        expected2.setLastName("Cena");
        expected2.setEmail("john@cena.test");
        expected2.setPhone("+1 234 567 890");

        assertThat(actual.getBody()).isEqualTo(new CustomerDto[]{expected1, expected2});
    }

    @Test
    void shouldReturnNotFoundWhenTheCustomerDoesNotExist() {
        ResponseEntity<Object> actual = restTemplate.getForEntity(String.format("%s/%d", URL, Long.MAX_VALUE), Object.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnTheCustomerRetrievingByItsId() {
        CustomerCreationRequest customerCreationRequest = new CustomerCreationRequest();
        customerCreationRequest.setUserId(1L);
        customerCreationRequest.setFirstName("John");
        customerCreationRequest.setLastName("Doe");
        customerCreationRequest.setEmail("john@doe.test");
        customerCreationRequest.setPhone("+1 234 567 890");
        ResponseEntity<CustomerCreationResponse> response = restTemplate.postForEntity(URL, customerCreationRequest, CustomerCreationResponse.class);

        Long id = Objects.requireNonNull(response.getBody()).getId();
        ResponseEntity<CustomerDto> actual = restTemplate.getForEntity(String.format("%s/%d", URL, id), CustomerDto.class);

        CustomerDto expected = new CustomerDto();
        expected.setId(id);
        expected.setUserId(1L);
        expected.setFirstName("John");
        expected.setLastName("Doe");
        expected.setEmail("john@doe.test");
        expected.setPhone("+1 234 567 890");
        assertThat(actual.getBody()).isEqualTo(expected);
    }
}