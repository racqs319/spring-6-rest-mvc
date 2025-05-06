package com.casesr.spring6restmvc.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.casesr.spring6restmvc.model.Customer;
import com.casesr.spring6restmvc.services.CustomerService;
import com.casesr.spring6restmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

  @Autowired MockMvc mockMvc;

  @MockitoBean CustomerService customerService;

  CustomerService customerServiceImpl = new CustomerServiceImpl();

  @Test
  void listCustomers() throws Exception {

    given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

    mockMvc
        .perform(get("/api/v1/customer").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()", is(2)));
  }

  @Test
  void getCustomerById() throws Exception {

    Customer testCustomer = customerServiceImpl.listCustomers().get(0);

    given(customerService.getCustomerById(testCustomer.getId())).willReturn(testCustomer);

    mockMvc
        .perform(get("/api/v1/customer/" + testCustomer.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
        .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));
  }
}
