package de.smava.homework.loan.api;

import de.smava.homework.loan.api.dto.CustomerAndLoansDto;
import de.smava.homework.loan.api.dto.LoanCreationRequest;
import de.smava.homework.loan.api.dto.LoanCreationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@RequestMapping("/api/loanapplications")
public interface LoanResource {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<LoanCreationResponse> createLoan(@RequestBody @Valid LoanCreationRequest loanCreationRequest);

    @GetMapping
    ResponseEntity<CustomerAndLoansDto> retrieveLoansByCustomerId(@RequestParam("customerId") Long customerId);
}
