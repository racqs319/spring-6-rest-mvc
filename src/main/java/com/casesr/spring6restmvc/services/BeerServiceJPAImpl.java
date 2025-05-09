package com.casesr.spring6restmvc.services;

import com.casesr.spring6restmvc.mappers.BeerMapper;
import com.casesr.spring6restmvc.model.BeerDTO;
import com.casesr.spring6restmvc.repositories.BeerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
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
    return beerRepository.findAll().stream().map(beerMapper::beerToBeerDto).toList();
  }

  @Override
  public Optional<BeerDTO> getBeerById(UUID id) {
    return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id).orElse(null)));
  }

  @Override
  public BeerDTO saveBeer(BeerDTO beer) {
    return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
  }

  @Override
  public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
    AtomicReference<Optional<BeerDTO>> updatedBeer = new AtomicReference<>();

    beerRepository
        .findById(beerId)
        .ifPresentOrElse(
            foundBeer -> {
              foundBeer.setBeerName(beer.getBeerName());
              foundBeer.setBeerStyle(beer.getBeerStyle());
              foundBeer.setUpc(beer.getUpc());
              foundBeer.setPrice(beer.getPrice());

              updatedBeer.set(
                  Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer))));
            },
            () -> updatedBeer.set(Optional.empty()));

    return updatedBeer.get();
  }

  @Override
  public Boolean deleteById(UUID beerId) {
    if (beerRepository.existsById(beerId)) {
      beerRepository.deleteById(beerId);
      return true;
    }

    return false;
  }

  @Override
  public void patchBeerById(UUID beerId, BeerDTO beer) {}
}
