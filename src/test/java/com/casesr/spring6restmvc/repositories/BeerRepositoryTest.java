package com.casesr.spring6restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.casesr.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BeerRepositoryTest {

  @Autowired BeerRepository beerRepository;

  @Test
  void testSaveBeer() {
    Beer savedBeer = beerRepository.save(Beer.builder().beerName("New Beer").build());

    assertThat(savedBeer).isNotNull();
    assertThat(savedBeer.getId()).isNotNull();
  }
}
