package com.casesr.spring6restmvc.mappers;

import com.casesr.spring6restmvc.entities.Customer;
import com.casesr.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

/**
 * @author Racquel.Cases
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {
  Customer customerDtoToCustomer(CustomerDTO customerDTO);

  CustomerDTO customerToCustomerDto(Customer customer);
}
