package de.smava.homework.loan.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Long>, JpaSpecificationExecutor<LoanEntity> {
    List<LoanEntity> findByCustomerId(Long customerId);
}
