package net.kerouad.accountservice.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.kerouad.accountservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {
    @GetMapping("/customers/{id}")
    @CircuitBreaker(name = "customerService", fallbackMethod = "getDefaultCustomer") // c'est un disgne pattern qui permet de résoudre le problème quand on appelle un service en panne il retourne un message par default jusqu'à ce que le service devient active
    Customer findCustomerById(@PathVariable Long id);
    @GetMapping("/customers")
    @CircuitBreaker(name = "customerService", fallbackMethod = "getDefaultCustomers")
    List<Customer> allCustomers();

    default Customer getDefaultCustomer(Long id, Exception exception) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName("Not available");
        customer.setLastName("Not available");
        customer.setEmail("Not available");
        return customer;
    }

    default List<Customer> getDefaultCustomers(Exception exception){
        return List.of();
    }
}
