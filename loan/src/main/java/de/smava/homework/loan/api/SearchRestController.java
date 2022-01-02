package de.smava.homework.loan.api;

import de.smava.homework.loan.client.Customer;
import de.smava.homework.loan.service.SearchService;
import de.smava.homework.loan.util.SearchCriteriaParser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class SearchRestController implements SearchResource {
    private final SearchService searchService;
    private final SearchCriteriaParser searchCriteriaParser;

    @Override
    public ResponseEntity<Collection<Customer>> search(String searchCriteria, Pageable pageable) {
        return ResponseEntity.ok(searchService.search(searchCriteriaParser.parse(searchCriteria), pageable));
    }
}