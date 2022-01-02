package de.smava.homework.loan.api.dto;

import de.smava.homework.loan.persistence.LoanEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanDto {
    private Long id;
    private BigDecimal amount;
    private Short duration;
    private LoanEntity.Status status;
}
