package com.casesr.spring6restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.casesr.spring6restmvc.entities.Customer;
import com.casesr.spring6restmvc.exception.NotFoundException;
import com.casesr.spring6restmvc.model.CustomerDTO;
import com.casesr.spring6restmvc.repositories.CustomerRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CustomerControllerIT {
  @Autowired private CustomerController customerController;

  @Autowired private CustomerRepository customerRepository;

  @Test
  void testListCustomers() {
    List<CustomerDTO> dtos = customerController.listCustomers();

    assertThat(dtos.size()).isEqualTo(2);
  }

  @Rollback
  @Transactional
  @Test
  void testEmptyListCustomers() {
    customerRepository.deleteAll();
    List<CustomerDTO> dtos = customerController.listCustomers();

    assertThat(dtos.size()).isEqualTo(0);
  }

  @Test
  void testGetCustomerById() {
    Customer customer = customerRepository.findAll().get(0);

    CustomerDTO dto = customerController.getCustomerById(customer.getId());
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isEqualTo(customer.getId());
  }

  @Test
  void testCustomerIdNotFound() {
    assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }
}
