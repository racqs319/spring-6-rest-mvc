package com.casesr.spring6restmvc.services;

import com.casesr.spring6restmvc.model.Beer;

import java.util.UUID;

/**
 * @author Racquel.Cases
 */
public interface BeerService {

    Beer getBeerById(UUID id);

}
