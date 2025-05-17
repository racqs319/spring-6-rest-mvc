package com.casesr.spring6restmvc.services;

import com.casesr.spring6restmvc.mappers.CustomerMapper;
import com.casesr.spring6restmvc.model.CustomerDTO;
import com.casesr.spring6restmvc.repositories.CustomerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    return customerMapper.customerToCustomerDto(
        customerRepository.save(customerMapper.customerDtoToCustomer(customer)));
  }

  @Override
  public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
    AtomicReference<Optional<CustomerDTO>> updatedCustomer = new AtomicReference<>();

    customerRepository
        .findById(customerId)
        .ifPresentOrElse(
            foundCustomer -> {
              foundCustomer.setCustomerName(customer.getCustomerName());

              updatedCustomer.set(
                  Optional.of(
                      customerMapper.customerToCustomerDto(
                          customerRepository.save(foundCustomer))));
            },
            () -> updatedCustomer.set(Optional.empty()));

    return updatedCustomer.get();
  }

  @Override
  public Boolean deleteById(UUID customerId) {
    if (customerRepository.existsById(customerId)) {
      customerRepository.deleteById(customerId);
      return true;
    }

    return false;
  }

  @Override
  public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer) {
    AtomicReference<Optional<CustomerDTO>> updatedCustomer = new AtomicReference<>();

    customerRepository
        .findById(customerId)
        .ifPresentOrElse(
            foundCustomer -> {
              if (StringUtils.hasText(customer.getCustomerName())) {
                foundCustomer.setCustomerName(customer.getCustomerName());
              }

              updatedCustomer.set(
                  Optional.of(
                      customerMapper.customerToCustomerDto(
                          customerRepository.save(foundCustomer))));
            },
            () -> updatedCustomer.set(Optional.empty()));

    return updatedCustomer.get();
  }
}
