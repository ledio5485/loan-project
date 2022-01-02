package de.smava.homework.loan.persistence;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false)
    private Long userId;

    @Column(updatable = true, nullable = false)
    private String firstName;

    @Column(updatable = true, nullable = false)
    private String lastName;

    @Column(updatable = true, nullable = false)
    private String email;

    @Column(updatable = true, nullable = false)
    private String phone;
}
