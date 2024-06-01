package com.example.kurs.service;

import com.example.kurs.exception.ResourceNotFoundException;
import com.example.kurs.model.Customer;
import com.example.kurs.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        log.info("Fetching all customers");
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        log.info("Fetching customer with id: {}", id);
        return customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found with id: {}", id);
                    return new ResourceNotFoundException("Customer not found with id " + id);
                });
    }

    @Override
    public Customer createCustomer(Customer customer) {
        log.info("Creating new customer with details: {}", customer);
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customerDetails) {
        log.info("Updating customer with id: {}", id);
        Customer customer = getCustomerById(id);
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setPhone(customerDetails.getPhone());
        customer.setSecondaryPhone(customerDetails.getSecondaryPhone());
        log.info("Customer with id: {} updated with new details: {}", id, customerDetails);
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        log.info("Deleting customer with id: {}", id);
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
        log.info("Customer with id: {} deleted", id);
    }
}
