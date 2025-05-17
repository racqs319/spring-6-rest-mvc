package com.casesr.spring6restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.casesr.spring6restmvc.entities.Customer;
import com.casesr.spring6restmvc.exception.NotFoundException;
import com.casesr.spring6restmvc.mappers.CustomerMapper;
import com.casesr.spring6restmvc.model.CustomerDTO;
import com.casesr.spring6restmvc.repositories.CustomerRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CustomerControllerIT {
  @Autowired private CustomerController customerController;

  @Autowired private CustomerRepository customerRepository;

  @Autowired private CustomerMapper customerMapper;

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
    assertThrows(
        NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
  }

  @Rollback
  @Transactional
  @Test
  void testSaveNewCustomer() {
    CustomerDTO customerDTO = CustomerDTO.builder().customerName("New Customer").build();

    ResponseEntity<CustomerDTO> response = customerController.handlePost(customerDTO);

    assertThat(response.getStatusCode().value()).isEqualTo(201);
    assertThat(response.getHeaders().getLocation()).isNotNull();

    String[] locationUUID = response.getHeaders().getLocation().getPath().split("/");
    UUID savedUUID = UUID.fromString(locationUUID[locationUUID.length - 1]);

    Customer savedCustomer = customerRepository.findById(savedUUID).orElse(null);
    assertThat(savedCustomer).isNotNull();
  }

  @Rollback
  @Transactional
  @Test
  void testUpdateExistingCustomer() {
    Customer customer = customerRepository.findAll().get(0);
    CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
    customerDTO.setId(null);
    customerDTO.setVersion(null);
    final String customerName = "Updated Customer";
    customerDTO.setCustomerName(customerName);

    ResponseEntity<CustomerDTO> response =
        customerController.updateById(customer.getId(), customerDTO);

    assertThat(response.getStatusCode().value()).isEqualTo(204);

    Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
    assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);
  }

  @Test
  void testUpdateCustomerNotFound() {
    assertThrows(
        NotFoundException.class,
        () -> customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build()));
  }

  @Rollback
  @Transactional
  @Test
  void testDeleteCustomerFound() {
    Customer customer = customerRepository.findAll().get(0);

    ResponseEntity<CustomerDTO> response = customerController.deleteById(customer.getId());

    assertThat(response.getStatusCode().value()).isEqualTo(204);
    assertThat(customerRepository.findById(customer.getId()).isEmpty()).isTrue();
  }

  @Test
  void testDeleteCustomerNotFound() {
    assertThrows(NotFoundException.class, () -> customerController.deleteById(UUID.randomUUID()));
  }

  @Rollback
  @Transactional
  @Test
  void testPatchExistingCustomer() {
    Customer customer = customerRepository.findAll().get(0);
    CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
    customerDTO.setId(null);
    customerDTO.setVersion(null);
    final String customerName = "Updated Customer";
    customerDTO.setCustomerName(customerName);

    ResponseEntity<CustomerDTO> response =
        customerController.patchCustomerById(customer.getId(), customerDTO);

    assertThat(response.getStatusCode().value()).isEqualTo(204);

    Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
    assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);
  }

  @Test
  void testPatchCustomerNotFound() {
    assertThrows(
        NotFoundException.class,
        () ->
            customerController.patchCustomerById(UUID.randomUUID(), CustomerDTO.builder().build()));
  }
}
