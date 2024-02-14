package com.example.SpringData_Transactional.Services;

import com.example.SpringData_Transactional.Entities.Customer;
import com.example.SpringData_Transactional.Exceptions.CustomerNotFoundException;
import com.example.SpringData_Transactional.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer with this is does not exist"));
    }

    public Customer updateCustomer( Long id, Customer customer) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with this id does not exist");

        }
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with this id does not exist");
        }
        customerRepository.deleteById(id);
    }

    public long getCustomerBalance(Long id){
        Customer customer = customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer with this is does not exist"));
        return customer.getBalance();
    }
}
