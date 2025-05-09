package com.casesr.spring6restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.casesr.spring6restmvc.entities.Beer;
import com.casesr.spring6restmvc.exception.NotFoundException;
import com.casesr.spring6restmvc.model.BeerDTO;
import com.casesr.spring6restmvc.repositories.BeerRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class BeerControllerIT {
  @Autowired BeerController beerController;

  @Autowired BeerRepository beerRepository;

  @Test
  void testListBeers() {
    List<BeerDTO> dtos = beerController.listBeers();

    assertThat(dtos.size()).isEqualTo(3);
  }

  @Rollback
  @Transactional
  @Test
  void testEmptyListBeers() {
    beerRepository.deleteAll();
    List<BeerDTO> dtos = beerController.listBeers();

    assertThat(dtos.size()).isEqualTo(0);
  }

  @Test
  void testGetById() {
    Beer beer = beerRepository.findAll().get(0);

    BeerDTO dto = beerController.getBeerById(beer.getId());

    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isEqualTo(beer.getId());
  }

  @Test
  void testBeerIdNotFound() {
    assertThrows(NotFoundException.class, () -> beerController.getBeerById(UUID.randomUUID()));
  }
}
