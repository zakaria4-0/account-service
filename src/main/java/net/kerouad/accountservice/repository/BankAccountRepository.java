package net.kerouad.accountservice.repository;

import net.kerouad.accountservice.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
