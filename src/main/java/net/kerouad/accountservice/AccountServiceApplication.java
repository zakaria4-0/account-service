package net.kerouad.accountservice;

import net.kerouad.accountservice.clients.CustomerRestClient;
import net.kerouad.accountservice.entities.BankAccount;
import net.kerouad.accountservice.enums.AccountType;
import net.kerouad.accountservice.repository.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository accountRepository, CustomerRestClient customerRestClient){
        return args -> {
            customerRestClient.allCustomers().forEach(customer -> {
                BankAccount bankAccount = BankAccount.builder()
                        .accountId(UUID.randomUUID().toString()) // UUID = universal user identifier
                        .currency("MAD")
                        .balance(98000)
                        .createdAt(LocalDate.now())
                        .type(AccountType.CURRENT_ACCOUNT)
                        .customerId(customer.getId())
                        .build();
                BankAccount bankAccount2 = BankAccount.builder()
                        .accountId(UUID.randomUUID().toString())
                        .currency("MAD")
                        .balance(12000)
                        .createdAt(LocalDate.now())
                        .type(AccountType.SAVING_ACCOUNT)
                        .customerId(customer.getId())
                        .build();
                accountRepository.saveAll(List.of(bankAccount, bankAccount2));
            });
        };
    }

}
