package de.smava.homework.loan.api;

import de.smava.homework.loan.client.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@RequestMapping("/search")
public interface SearchResource {

    @GetMapping
    ResponseEntity<Collection<Customer>> search(@RequestParam("criteria") String criteria, @PageableDefault(sort = "id") Pageable pageable);
}
