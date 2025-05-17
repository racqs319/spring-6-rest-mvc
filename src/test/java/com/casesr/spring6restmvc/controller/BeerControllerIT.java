package com.casesr.spring6restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.casesr.spring6restmvc.entities.Beer;
import com.casesr.spring6restmvc.exception.NotFoundException;
import com.casesr.spring6restmvc.mappers.BeerMapper;
import com.casesr.spring6restmvc.model.BeerDTO;
import com.casesr.spring6restmvc.repositories.BeerRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class BeerControllerIT {
  @Autowired BeerController beerController;

  @Autowired BeerRepository beerRepository;

  @Autowired BeerMapper beerMapper;

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

  @Rollback
  @Transactional
  @Test
  void testSaveNewBeer() {
    BeerDTO beerDTO = BeerDTO.builder().beerName("New Beer").build();

    ResponseEntity<BeerDTO> response = beerController.handlePost(beerDTO);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
    assertThat(response.getHeaders().getLocation()).isNotNull();

    String[] locationUUID = response.getHeaders().getLocation().getPath().split("/");
    UUID savedUUID = UUID.fromString(locationUUID[locationUUID.length - 1]);

    Beer savedBeer = beerRepository.findById(savedUUID).orElse(null);
    assertThat(savedBeer).isNotNull();
  }

  @Rollback
  @Transactional
  @Test
  void testUpdateExistingBeer() {
    Beer beer = beerRepository.findAll().get(0);
    BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
    beerDTO.setId(null);
    beerDTO.setVersion(null);
    final String beerName = "Updated Beer";
    beerDTO.setBeerName(beerName);

    ResponseEntity<BeerDTO> response = beerController.updateById(beer.getId(), beerDTO);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

    Beer updatedBeer = beerRepository.findById(beer.getId()).get();
    assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
  }

  @Test
  void testUpdateBeerNotFound() {
    assertThrows(
        NotFoundException.class,
        () -> beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build()));
  }

  @Rollback
  @Transactional
  @Test
  void testDeleteBeerFound() {
    Beer beer = beerRepository.findAll().get(0);

    ResponseEntity<BeerDTO> response = beerController.deleteById(beer.getId());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    assertThat(beerRepository.findById(beer.getId()).isEmpty()).isTrue();
  }

  @Test
  void testDeleteBeerNotFound() {
    assertThrows(NotFoundException.class, () -> beerController.deleteById(UUID.randomUUID()));
  }

  @Rollback
  @Transactional
  @Test
  void testPatchExistingBeer() {
    Beer beer = beerRepository.findAll().get(0);
    BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
    beerDTO.setId(null);
    beerDTO.setVersion(null);
    final String beerName = "Updated Beer";
    beerDTO.setBeerName(beerName);
    final BigDecimal beerPrice = new BigDecimal("10.00");
    beerDTO.setPrice(beerPrice);

    ResponseEntity<BeerDTO> response = beerController.patchBeerById(beer.getId(), beerDTO);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

    Beer updatedBeer = beerRepository.findById(beer.getId()).get();
    assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    assertThat(updatedBeer.getPrice()).isEqualTo(beerPrice);
  }

  @Test
  void testPatchBeerNotFound() {
    assertThrows(
        NotFoundException.class,
        () -> beerController.patchBeerById(UUID.randomUUID(), BeerDTO.builder().build()));
  }
}
