package com.casesr.spring6restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.casesr.spring6restmvc.model.Beer;
import com.casesr.spring6restmvc.services.BeerService;
import com.casesr.spring6restmvc.services.BeerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

  @Autowired MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  @MockitoBean BeerService beerService;

  @Captor ArgumentCaptor<UUID> uuidArgumentCaptor;

  BeerService beerServiceImpl;

  @BeforeEach
  void setUp() {
    beerServiceImpl = new BeerServiceImpl();
  }

  @Test
  public void getBeerById() throws Exception {

    Beer testBeer = beerServiceImpl.listBeers().get(0);

    given(beerService.getBeerById(any(UUID.class))).willReturn(testBeer);

    mockMvc
        .perform(get("/api/v1/beer/" + testBeer.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
        .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
  }

  @Test
  void testListBeers() throws Exception {
    given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

    mockMvc
        .perform(get("/api/v1/beer").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()", is(3)));
  }

  @Test
  void testCreateNewBeer() throws Exception {

    Beer testBeer = beerServiceImpl.listBeers().get(0);
    testBeer.setId(null);
    testBeer.setVersion(null);

    given(beerService.saveBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));

    mockMvc
        .perform(
            post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeer)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"));
  }

  @Test
  void testUpdateBeer() throws Exception {

    Beer testBeer = beerServiceImpl.listBeers().get(0);

    mockMvc
        .perform(
            put("/api/v1/beer/" + testBeer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeer)))
        .andExpect(status().isNoContent());

    verify(beerService).updateBeerById(uuidArgumentCaptor.capture(), any(Beer.class));

    assertThat(testBeer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
  }

  @Test
  void testDeleteBeer() throws Exception {

    Beer testBeer = beerServiceImpl.listBeers().get(0);

    mockMvc
        .perform(delete("/api/v1/beer/" + testBeer.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(beerService).deleteById(uuidArgumentCaptor.capture());

    assertThat(testBeer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
  }
}
