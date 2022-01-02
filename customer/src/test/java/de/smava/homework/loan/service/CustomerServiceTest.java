package de.smava.homework.loan.service;

import de.smava.homework.loan.persistence.CustomerEntity;
import de.smava.homework.loan.persistence.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService sut;

    @Test
    void shouldCreateNewCustomer() {
        CustomerEntity newCustomerEntity = mock(CustomerEntity.class);
        CustomerEntity savedCustomerEntity = mock(CustomerEntity.class);
        when(repository.save(newCustomerEntity)).thenReturn(savedCustomerEntity);

        CustomerEntity actual = sut.createCustomer(newCustomerEntity);

        assertThat(actual).isEqualTo(savedCustomerEntity);
    }

    @Test
    void shouldGetCustomersByIds() {
        Set<Long> ids = new HashSet<Long>() {{
            add(1L);
            add(2L);
        }};

        List<CustomerEntity> expected = Collections.singletonList(mock(CustomerEntity.class));
        when(repository.findAllById(ids)).thenReturn(expected);

        Collection<CustomerEntity> actual = sut.getCustomers(ids);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetCustomerById() {
        CustomerEntity expected = mock(CustomerEntity.class);
        when(repository.findById(1L)).thenReturn(Optional.of(expected));

        Optional<CustomerEntity> actual = sut.getCustomer(1L);

        assertThat(actual).isEqualTo(Optional.of(expected));
    }
}