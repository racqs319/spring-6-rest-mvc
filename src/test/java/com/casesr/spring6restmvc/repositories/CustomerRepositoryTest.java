package com.casesr.spring6restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.casesr.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {

  @Autowired CustomerRepository customerRepository;

  @Test
  void testSaveCustomer() {
    Customer savedCustomer =
        customerRepository.save(Customer.builder().customerName("New Customer").build());

    assertThat(savedCustomer).isNotNull();
    assertThat(savedCustomer.getId()).isNotNull();
  }
}
