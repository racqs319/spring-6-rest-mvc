package com.casesr.spring6restmvc.services;

import com.casesr.spring6restmvc.model.BeerDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Racquel.Cases
 */
public interface BeerService {

  List<BeerDTO> listBeers();

  Optional<BeerDTO> getBeerById(UUID id);

  BeerDTO saveBeer(BeerDTO beer);

  Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

  Boolean deleteById(UUID beerId);

  void patchBeerById(UUID beerId, BeerDTO beer);
}
