package com.casesr.spring6restmvc.services;

import com.casesr.spring6restmvc.model.CustomerDTO;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Racquel.Cases
 */
@Service
public class CustomerServiceImpl implements CustomerService {

  private Map<UUID, CustomerDTO> customerMap;

  public CustomerServiceImpl() {

    CustomerDTO customer1 =
        CustomerDTO.builder()
            .id(UUID.randomUUID())
            .customerName("John Smith")
            .version(1)
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();

    CustomerDTO customer2 =
        CustomerDTO.builder()
            .id(UUID.randomUUID())
            .customerName("Juan dela Cruz")
            .version(1)
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();

    customerMap = new HashMap<>();
    customerMap.put(customer1.getId(), customer1);
    customerMap.put(customer2.getId(), customer2);
  }

  @Override
  public List<CustomerDTO> listCustomers() {
    return List.copyOf(customerMap.values());
  }

  @Override
  public Optional<CustomerDTO> getCustomerById(UUID id) {
    return Optional.of(customerMap.get(id));
  }

  @Override
  public CustomerDTO saveCustomer(CustomerDTO customer) {

    CustomerDTO savedCustomer =
        CustomerDTO.builder()
            .id(UUID.randomUUID())
            .customerName(customer.getCustomerName())
            .version(customer.getVersion())
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();

    customerMap.put(savedCustomer.getId(), savedCustomer);

    return savedCustomer;
  }

  @Override
  public void updateCustomerById(UUID customerId, CustomerDTO customer) {

    CustomerDTO existing = customerMap.get(customerId);
    existing.setCustomerName(customer.getCustomerName());
    existing.setLastModifiedDate(LocalDateTime.now());
  }

  @Override
  public void deleteById(UUID customerId) {

    customerMap.remove(customerId);
  }

  @Override
  public void patchCustomerById(UUID customerId, CustomerDTO customer) {

    CustomerDTO existing = customerMap.get(customerId);

    if (StringUtils.hasText(customer.getCustomerName())) {
      existing.setCustomerName(customer.getCustomerName());
    }

    existing.setLastModifiedDate(LocalDateTime.now());
  }
}
