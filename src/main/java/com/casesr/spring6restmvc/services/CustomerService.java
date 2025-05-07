package com.casesr.spring6restmvc.services;

import com.casesr.spring6restmvc.model.CustomerDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Racquel.Cases
 */
public interface CustomerService {

  List<CustomerDTO> listCustomers();

  Optional<CustomerDTO> getCustomerById(UUID id);

  CustomerDTO saveCustomer(CustomerDTO customer);

  void updateCustomerById(UUID customerId, CustomerDTO customer);

  void deleteById(UUID customerId);

  void patchCustomerById(UUID customerId, CustomerDTO customer);
}
