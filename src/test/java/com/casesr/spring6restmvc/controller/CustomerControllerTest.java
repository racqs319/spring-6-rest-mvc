package com.casesr.spring6restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.casesr.spring6restmvc.model.Customer;
import com.casesr.spring6restmvc.services.CustomerService;
import com.casesr.spring6restmvc.services.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

  @Autowired MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  @MockitoBean CustomerService customerService;

  @Captor ArgumentCaptor<UUID> uuidArgumentCaptor;

  @Captor ArgumentCaptor<Customer> customerArgumentCaptor;

  CustomerService customerServiceImpl;

  @BeforeEach
  void setUp() {
    customerServiceImpl = new CustomerServiceImpl();
  }

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

  @Test
  void testCreateNewCustomer() throws Exception {

    Customer testCustomer = customerServiceImpl.listCustomers().get(0);
    testCustomer.setId(null);
    testCustomer.setVersion(null);

    given(customerService.saveCustomer(any(Customer.class)))
        .willReturn(customerServiceImpl.listCustomers().get(1));

    mockMvc
        .perform(
            post("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"));
  }

  @Test
  void testUpdateCustomer() throws Exception {

    Customer testCustomer = customerServiceImpl.listCustomers().get(0);

    mockMvc
        .perform(
            put("/api/v1/customer/" + testCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
        .andExpect(status().isNoContent());

    verify(customerService).updateCustomerById(uuidArgumentCaptor.capture(), any(Customer.class));

    assertThat(testCustomer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
  }

  @Test
  void testDeleteCustomer() throws Exception {

    Customer testCustomer = customerServiceImpl.listCustomers().get(0);

    mockMvc
        .perform(
            delete("/api/v1/customer/" + testCustomer.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(customerService).deleteById(uuidArgumentCaptor.capture());

    assertThat(testCustomer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
  }

  @Test
  void testPatchCustomer() throws Exception {

    Customer customer = customerServiceImpl.listCustomers().get(0);

    Map<String, Object> customerMap = new HashMap<>();
    customerMap.put("customerName", "New Customer Name");

    mockMvc
        .perform(
            patch("/api/v1/customer/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerMap)))
        .andExpect(status().isNoContent());

    verify(customerService)
        .patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());

    assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    assertThat(customerMap.get("customerName"))
        .isEqualTo(customerArgumentCaptor.getValue().getCustomerName());
  }
}
