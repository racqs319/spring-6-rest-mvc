package com.casesr.spring6restmvc.controller;

import com.casesr.spring6restmvc.exception.NotFoundException;
import com.casesr.spring6restmvc.model.BeerDTO;
import com.casesr.spring6restmvc.services.BeerService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Racquel.Cases
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class BeerController {

  public static final String BEER_PATH = "/api/v1/beer";
  public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

  private final BeerService beerService;

  @GetMapping(BEER_PATH)
  public List<BeerDTO> listBeers() {

    return beerService.listBeers();
  }

  @GetMapping(BEER_PATH_ID)
  public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {

    log.debug("Get Beer by Id - in controller");

    return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
  }

  @PostMapping(BEER_PATH)
  public ResponseEntity<BeerDTO> handlePost(@RequestBody BeerDTO beer) {

    BeerDTO savedBeer = beerService.saveBeer(beer);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

    return new ResponseEntity<>(headers, HttpStatus.CREATED);
  }

  @PutMapping(BEER_PATH_ID)
  public ResponseEntity<BeerDTO> updateById(
      @PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {

    beerService.updateBeerById(beerId, beer);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping(BEER_PATH_ID)
  public ResponseEntity<BeerDTO> deleteById(@PathVariable("beerId") UUID beerId) {

    beerService.deleteById(beerId);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PatchMapping(BEER_PATH_ID)
  public ResponseEntity<BeerDTO> updateBeerPatchById(
      @PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {

    beerService.patchBeerById(beerId, beer);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
