package com.casesr.spring6restmvc.repositories;

import com.casesr.spring6restmvc.entities.Customer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Racquel.Cases
 */
public interface CustomerRepository extends JpaRepository<Customer, UUID> {}
