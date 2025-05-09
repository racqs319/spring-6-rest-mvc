package com.casesr.spring6restmvc.services;

import com.casesr.spring6restmvc.mappers.CustomerMapper;
import com.casesr.spring6restmvc.model.CustomerDTO;
import com.casesr.spring6restmvc.repositories.CustomerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @author Racquel.Cases
 */
@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPAImpl implements CustomerService {
  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  @Override
  public List<CustomerDTO> listCustomers() {
    return customerRepository.findAll().stream()
        .map(customerMapper::customerToCustomerDto)
        .toList();
  }

  @Override
  public Optional<CustomerDTO> getCustomerById(UUID id) {
    return Optional.ofNullable(
        customerMapper.customerToCustomerDto(customerRepository.findById(id).orElse(null)));
  }

  @Override
  public CustomerDTO saveCustomer(CustomerDTO customer) {
    return null;
  }

  @Override
  public void updateCustomerById(UUID customerId, CustomerDTO customer) {}

  @Override
  public void deleteById(UUID customerId) {}

  @Override
  public void patchCustomerById(UUID customerId, CustomerDTO customer) {}
}
