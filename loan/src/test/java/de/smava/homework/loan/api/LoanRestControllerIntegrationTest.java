package de.smava.homework.loan.api;

import de.smava.homework.loan.AbstractIntegrationTest;
import de.smava.homework.loan.api.dto.CustomerAndLoansDto;
import de.smava.homework.loan.api.dto.CustomerDto;
import de.smava.homework.loan.api.dto.LoanCreationRequest;
import de.smava.homework.loan.api.dto.LoanCreationResponse;
import de.smava.homework.loan.api.dto.LoanDto;
import de.smava.homework.loan.client.Customer;
import de.smava.homework.loan.persistence.LoanEntity;
import de.smava.homework.loan.persistence.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.mockserver.model.MediaType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LoanRestControllerIntegrationTest extends AbstractIntegrationTest {
    private static final String URL = "/api/loanapplications";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LoanRepository repository;

    @BeforeEach
    void setUp() {
        mockServerClient.reset();
    }

    @Test
    void shouldCreateNewLoan() {
        LoanCreationRequest loanCreationRequest = new LoanCreationRequest();
        loanCreationRequest.setCustomerId(1L);
        loanCreationRequest.setAmount(new BigDecimal("0.01"));
        loanCreationRequest.setDuration(Short.MAX_VALUE);
        loanCreationRequest.setStatus(LoanEntity.Status.CREATED);

        ResponseEntity<LoanCreationResponse> response = restTemplate.postForEntity(URL, loanCreationRequest, LoanCreationResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long loanId = Objects.requireNonNull(response.getBody()).getId();
        assertThat(loanId).isNotNull();

        LoanEntity actual = repository.findById(loanId).orElseThrow(() -> new RuntimeException(String.format("Loan %d not found.", loanId)));

        LoanEntity expected = new LoanEntity();
        expected.setId(loanId);
        expected.setCustomerId(loanCreationRequest.getCustomerId());
        expected.setAmount(loanCreationRequest.getAmount());
        expected.setDuration(loanCreationRequest.getDuration());
        expected.setStatus(loanCreationRequest.getStatus());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetLoansByCustomerId() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setUserId(2L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@doe.test");
        customer.setPhone("+1 234 567 890");
        mockCustomerRequest(customer);

        LoanCreationRequest loanCreationRequest = new LoanCreationRequest();
        loanCreationRequest.setCustomerId(1L);
        loanCreationRequest.setAmount(new BigDecimal("0.01"));
        loanCreationRequest.setDuration(Short.MAX_VALUE);
        loanCreationRequest.setStatus(LoanEntity.Status.CREATED);

        restTemplate.postForEntity(URL, loanCreationRequest, LoanCreationResponse.class);

        LoanCreationRequest loanCreationRequest2 = new LoanCreationRequest();
        loanCreationRequest2.setCustomerId(2L);
        loanCreationRequest2.setAmount(new BigDecimal("1.01"));
        loanCreationRequest2.setDuration(Short.MAX_VALUE);
        loanCreationRequest2.setStatus(LoanEntity.Status.DENIED);

        restTemplate.postForEntity(URL, loanCreationRequest2, LoanCreationResponse.class);

        ResponseEntity<CustomerAndLoansDto> actual = restTemplate.getForEntity(String.format("%s?customerId=%d", URL, 1L), CustomerAndLoansDto.class);

        CustomerDto expectedCustomer = new CustomerDto();
        BeanUtils.copyProperties(customer, expectedCustomer);
        List<LoanEntity> loans = repository.findByCustomerId(customer.getId());
        assertThat(loans.size()).isEqualTo(1);
        LoanDto expectedLoan = new LoanDto();
        BeanUtils.copyProperties(loans.get(0), expectedLoan);
        CustomerAndLoansDto expected = new CustomerAndLoansDto();
        expected.setCustomer(expectedCustomer);
        expected.setLoans(Collections.singletonList(expectedLoan));
        assertThat(actual.getBody()).isEqualTo(expected);
    }

    private void mockCustomerRequest(Customer customer) {
        mockServerClient
                .when(HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath(String.format("/api/customers/%d", customer.getId()))
                )
                .respond(HttpResponse.response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(JsonBody.json(customer)));
    }
}