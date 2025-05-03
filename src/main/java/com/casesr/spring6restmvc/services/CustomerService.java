package com.casesr.spring6restmvc.services;

import com.casesr.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

/**
 * @author Racquel.Cases
 */
public interface CustomerService {

    List<Customer> listCustomers();

    Customer getCustomerById(UUID id);

    Customer saveCustomer(Customer customer);
}
