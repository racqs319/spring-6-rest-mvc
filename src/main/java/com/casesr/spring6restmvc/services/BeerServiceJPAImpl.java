package com.casesr.spring6restmvc.services;

import com.casesr.spring6restmvc.mappers.BeerMapper;
import com.casesr.spring6restmvc.model.BeerDTO;
import com.casesr.spring6restmvc.repositories.BeerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @author Racquel.Cases
 */
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPAImpl implements BeerService {
  private final BeerRepository beerRepository;
  private final BeerMapper beerMapper;

  @Override
  public List<BeerDTO> listBeers() {
    return List.of();
  }

  @Override
  public Optional<BeerDTO> getBeerById(UUID id) {
    return Optional.empty();
  }

  @Override
  public BeerDTO saveBeer(BeerDTO beer) {
    return null;
  }

  @Override
  public void updateBeerById(UUID beerId, BeerDTO beer) {}

  @Override
  public void deleteById(UUID beerId) {}

  @Override
  public void patchBeerById(UUID beerId, BeerDTO beer) {}
}
