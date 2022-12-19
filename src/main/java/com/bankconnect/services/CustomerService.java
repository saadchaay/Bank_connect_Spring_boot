package com.bankconnect.services;

import com.bankconnect.entities.Customer;
import com.bankconnect.helpers.Enum;
import com.bankconnect.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public List<Customer> getPendingCustomers(){
        return getAllCustomers().stream()
                .filter(customer -> !customer.getStatus())
                .collect(Collectors.toList());
    }

    public Customer getCustomerById(Long id){
        Optional customer = customerRepository.findById(id);
        return customer.isPresent()? (Customer) customer.get() : null;
    }

    public void deleteCustomerById(Long id){
        if(getCustomerById(id) != null)
            customerRepository.deleteById(id);
    }

    public void confirmCustomerById(Long id){
        if(getCustomerById(id) != null){
            getCustomerById(id).setStatus(true);
        }
    }

//    public void rejectCustomerById(Long id){
//        if(getCustomerById(id) != null){
//            getCustomerById(id).setStatus(String.valueOf(Enum.statusVal.Rejected));
//        }
//    }

    public void activateAccount(Long id){
        customerRepository.activateAccount(true, id);
    }

    public UserDetails findByEmail(String email) {
        Customer user = customerRepository.findAll()
                .stream()
                .filter(u -> (u.getEmail()).equals(email) && u.getStatus())
                .findFirst()
                .orElse(null);
        return user != null ? new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(Enum.role.CUSTOMER.toString()))) : null ;
    }
}
