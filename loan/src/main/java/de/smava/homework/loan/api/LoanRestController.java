package de.smava.homework.loan.api;

import de.smava.homework.loan.api.dto.CustomerAndLoansDto;
import de.smava.homework.loan.api.dto.LoanCreationRequest;
import de.smava.homework.loan.api.dto.LoanCreationResponse;
import de.smava.homework.loan.persistence.LoanEntity;
import de.smava.homework.loan.service.CustomerAndLoans;
import de.smava.homework.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class LoanRestController implements LoanResource {
    private final LoanService loanService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<LoanCreationResponse> createLoan(LoanCreationRequest loanCreationRequest) {
        LoanEntity loanEntityToBeCreated = Objects.requireNonNull(conversionService.convert(loanCreationRequest, LoanEntity.class));
        LoanEntity createdLoan = loanService.createLoan(loanEntityToBeCreated);
        LoanCreationResponse loanCreationResponse = new LoanCreationResponse();
        loanCreationResponse.setId(createdLoan.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(loanCreationResponse);
    }

    @Override
    public ResponseEntity<CustomerAndLoansDto> retrieveLoansByCustomerId(Long customerId) {
        CustomerAndLoans customerAndLoans = loanService.retrieveLoansByCustomerId(customerId);
        CustomerAndLoansDto customerAndLoansDto = Objects.requireNonNull(conversionService.convert(customerAndLoans, CustomerAndLoansDto.class));

        return ResponseEntity.ok(customerAndLoansDto);
    }
}
