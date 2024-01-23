package net.kerouad.accountservice.entities;

import jakarta.persistence.*;
import lombok.*;
import net.kerouad.accountservice.enums.AccountType;
import net.kerouad.accountservice.model.Customer;

import java.time.LocalDate;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class BankAccount {
    @Id
    private String accountId;
    private double balance;
    private LocalDate createdAt;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @Transient
    private Customer customer;
    private Long customerId;
}
