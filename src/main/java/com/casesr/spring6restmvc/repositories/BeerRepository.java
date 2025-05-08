package com.casesr.spring6restmvc.repositories;

import com.casesr.spring6restmvc.entities.Beer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Racquel.Cases
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {}
