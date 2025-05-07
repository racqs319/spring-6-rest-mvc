package com.casesr.spring6restmvc.controller;

import com.casesr.spring6restmvc.exception.NotFoundException;
import com.casesr.spring6restmvc.model.Customer;
import com.casesr.spring6restmvc.services.CustomerService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Racquel.Cases
 */
@RestController
@AllArgsConstructor
public class CustomerController {

  public static final String CUSTOMER_PATH = "/api/v1/customer";
  public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

  private final CustomerService customerService;

  @GetMapping(CUSTOMER_PATH)
  public List<Customer> listCustomers() {

    return customerService.listCustomers();
  }

  @GetMapping(CUSTOMER_PATH_ID)
  public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {

    return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
  }

  @PostMapping(CUSTOMER_PATH)
  public ResponseEntity<Customer> handlePost(@RequestBody Customer customer) {

    Customer savedCustomer = customerService.saveCustomer(customer);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());

    return new ResponseEntity<>(headers, HttpStatus.CREATED);
  }

  @PutMapping(CUSTOMER_PATH_ID)
  public ResponseEntity<Customer> updateById(
      @PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {

    customerService.updateCustomerById(customerId, customer);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping(CUSTOMER_PATH_ID)
  public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId) {

    customerService.deleteById(customerId);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PatchMapping(CUSTOMER_PATH_ID)
  public ResponseEntity patchCustomerById(
      @PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {

    customerService.patchCustomerById(customerId, customer);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
