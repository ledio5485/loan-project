package de.smava.homework.loan.service;

import de.smava.homework.loan.client.Customer;
import de.smava.homework.loan.persistence.LoanEntity;
import de.smava.homework.loan.persistence.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService {
    private final CustomerService customerService;
    private final LoanRepository loanRepository;

    @Transactional
    public LoanEntity createLoan(LoanEntity newLoanEntity) {
        log.info("Saving new loan {}.", newLoanEntity);

        LoanEntity createdLoan = loanRepository.save(newLoanEntity);
        log.info("Loan {} was created successfully.", createdLoan);

        return createdLoan;
    }

    public CustomerAndLoans retrieveLoansByCustomerId(Long customerId) {
        log.info("Getting the loans for customer {}", customerId);
        Customer customer = customerService.retrieveCustomer(customerId);
        List<LoanEntity> loans = loanRepository.findByCustomerId(customerId);

        log.info("Found {} loans for the customer {}", loans.size(), customer);
        CustomerAndLoans customerAndLoans = new CustomerAndLoans();
        customerAndLoans.setCustomer(customer);
        customerAndLoans.setLoans(loans);

        return customerAndLoans;
    }
}
