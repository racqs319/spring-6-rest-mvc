package com.casesr.spring6restmvc.mappers;

import com.casesr.spring6restmvc.entities.Beer;
import com.casesr.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

/**
 * @author Racquel.Cases
 */
@Mapper
public interface BeerMapper {
  Beer beerDtoToBeer(BeerDTO beerDTO);

  BeerDTO beerToBeerDto(Beer beer);
}
