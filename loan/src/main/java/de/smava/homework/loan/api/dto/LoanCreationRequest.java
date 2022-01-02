package de.smava.homework.loan.api.dto;

import de.smava.homework.loan.persistence.LoanEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class LoanCreationRequest {
    @NotNull
    private Long customerId;

    @Positive
    private BigDecimal amount;

    @Positive
    private Short duration;

    @NotNull
    private LoanEntity.Status status;
}
