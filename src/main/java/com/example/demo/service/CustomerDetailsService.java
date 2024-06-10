package com.example.demo.service;

import com.example.demo.bean.model.CustomerDetail;
import com.example.demo.bean.request.CustomerRequest;
import com.example.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Lazy
    @Autowired
    private PasswordEncoder encoder;

    Logger logger = LoggerFactory.getLogger(CustomerDetailsService.class);

    public CustomerDetail retrieveCustomer(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public CustomerDetail retriveCustomerByUserName(String userName){
        return customerRepository.findByUserName(userName).orElse(null);
    }

    public CustomerDetail updateCustomer(CustomerDetail customer){
        customer.setPassword(encoder.encode(customer.getPassword()));
        return customerRepository.saveAndFlush(customer);
    }

    public List<CustomerDetail> findAll(){
        return customerRepository.findAll();
    }

    public CustomerDetail createCustomer(CustomerRequest request){
        try {
            CustomerDetail customer = new CustomerDetail(request.getName(), request.getUserName(), request.getPassword(), request.getAddress());
            return customerRepository.saveAndFlush(customer);
        } finally {
            logger.info("Customer added successfully");
        }
    }

    public void deleteCustomer(CustomerDetail customer){
        customerRepository.delete(customer);
    }

}
