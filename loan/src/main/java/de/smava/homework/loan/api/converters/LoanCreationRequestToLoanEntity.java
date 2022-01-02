package de.smava.homework.loan.api.converters;

import de.smava.homework.loan.api.dto.LoanCreationRequest;
import de.smava.homework.loan.persistence.LoanEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanCreationRequestToLoanEntity implements Converter<LoanCreationRequest,LoanEntity> {

    @Override
    public LoanEntity convert(LoanCreationRequest loanCreationRequest) {
        LoanEntity loanEntity = new LoanEntity();
        BeanUtils.copyProperties(loanCreationRequest, loanEntity);

        return loanEntity;
    }
}
