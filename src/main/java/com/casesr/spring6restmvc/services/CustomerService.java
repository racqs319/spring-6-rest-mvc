package com.casesr.spring6restmvc.services;

import com.casesr.spring6restmvc.model.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Racquel.Cases
 */
public interface CustomerService {

  List<Customer> listCustomers();

  Optional<Customer> getCustomerById(UUID id);

  Customer saveCustomer(Customer customer);

  void updateCustomerById(UUID customerId, Customer customer);

  void deleteById(UUID customerId);

  void patchCustomerById(UUID customerId, Customer customer);
}
