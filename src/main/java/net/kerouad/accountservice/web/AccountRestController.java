package net.kerouad.accountservice.web;

import lombok.AllArgsConstructor;
import net.kerouad.accountservice.clients.CustomerRestClient;
import net.kerouad.accountservice.entities.BankAccount;
import net.kerouad.accountservice.model.Customer;
import net.kerouad.accountservice.repository.BankAccountRepository;
import org.apache.http.conn.util.PublicSuffixList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AccountRestController {
    private BankAccountRepository bankAccountRepository;
    private CustomerRestClient customerRestClient;

    @GetMapping("/accounts")
    public List<BankAccount> accountList(){
        List<BankAccount> accounts = bankAccountRepository.findAll();
        accounts.forEach(bankAccount -> {
            Customer customer = customerRestClient.findCustomerById(bankAccount.getCustomerId());
            bankAccount.setCustomer(customer);
        });
        return accounts;
    }

    @GetMapping("/accounts/{id}")
    public BankAccount bankAccountById(@PathVariable String id){
        BankAccount bankAccount = bankAccountRepository.findById(id).get();
        Customer customer = customerRestClient.findCustomerById(bankAccount.getCustomerId());
        bankAccount.setCustomer(customer);
        return bankAccount;
    }


}
