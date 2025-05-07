package com.casesr.spring6restmvc.controller;

import com.casesr.spring6restmvc.exception.NotFoundException;
import com.casesr.spring6restmvc.model.Beer;
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
  public List<Beer> listBeers() {

    return beerService.listBeers();
  }

  @GetMapping(BEER_PATH_ID)
  public Beer getBeerById(@PathVariable("beerId") UUID beerId) {

    log.debug("Get Beer by Id - in controller");

    return beerService.getBeerById(beerId);
  }

  @PostMapping(BEER_PATH)
  public ResponseEntity<Beer> handlePost(@RequestBody Beer beer) {

    Beer savedBeer = beerService.saveBeer(beer);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

    return new ResponseEntity<>(headers, HttpStatus.CREATED);
  }

  @PutMapping(BEER_PATH_ID)
  public ResponseEntity<Beer> updateById(
      @PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {

    beerService.updateBeerById(beerId, beer);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping(BEER_PATH_ID)
  public ResponseEntity<Beer> deleteById(@PathVariable("beerId") UUID beerId) {

    beerService.deleteById(beerId);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PatchMapping(BEER_PATH_ID)
  public ResponseEntity<Beer> updateBeerPatchById(
      @PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {

    beerService.patchBeerById(beerId, beer);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Beer> handleNotFound() {
    return ResponseEntity.notFound().build();
  }
}
