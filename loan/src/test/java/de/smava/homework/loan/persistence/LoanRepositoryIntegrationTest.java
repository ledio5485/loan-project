package de.smava.homework.loan.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class LoanRepositoryIntegrationTest {

    @Autowired
    private LoanRepository sut;

    @Test
    void shouldFindLoanByCustomerId() {
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setCustomerId(1L);
        loanEntity.setAmount(new BigDecimal("12.34"));
        loanEntity.setDuration((short) 3);
        loanEntity.setStatus(LoanEntity.Status.CREATED);
        LoanEntity expectedLoanEntity = sut.save(loanEntity);

        List<LoanEntity> actual = sut.findByCustomerId(1L);

        List<LoanEntity> expected = Collections.singletonList(expectedLoanEntity);
        assertThat(actual).isEqualTo(expected);
    }
}