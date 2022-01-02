package de.smava.homework.loan.service;

import de.smava.homework.loan.client.Customer;
import de.smava.homework.loan.persistence.EntitySpecification;
import de.smava.homework.loan.persistence.LoanEntity;
import de.smava.homework.loan.persistence.LoanRepository;
import de.smava.homework.loan.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final CustomerService customerService;
    private final LoanRepository loanRepository;

    public Collection<Customer> search(List<SearchCriteria> criteriaList, Pageable pageable) {
        log.info("Searching for {}, paged {}", criteriaList, pageable);
        List<Specification<LoanEntity>> specificationList = criteriaList.stream()
                .map(EntitySpecification<LoanEntity>::new)
                .collect(Collectors.toList());
        Page<LoanEntity> page;
        if (specificationList.isEmpty()) {
            page = loanRepository.findAll(pageable);
        } else {
            Specification<LoanEntity> specification = specificationList.stream()
                    .reduce(specificationList.get(0), Specification::and);
            page = loanRepository.findAll(Specification.where(specification), pageable);
        }
        Set<Long> customerIds = page.get()
                .map(LoanEntity::getCustomerId)
                .collect(Collectors.toSet());
        log.info("Found customers {} meeting the criteriaList provided.", customerIds);
        return customerIds.isEmpty() ? Collections.emptyList() : customerService.retrieveCustomers(customerIds);
    }
}
