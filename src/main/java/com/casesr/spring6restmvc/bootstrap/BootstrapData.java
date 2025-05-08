package com.casesr.spring6restmvc.bootstrap;

import com.casesr.spring6restmvc.entities.Beer;
import com.casesr.spring6restmvc.entities.Customer;
import com.casesr.spring6restmvc.model.BeerStyle;
import com.casesr.spring6restmvc.repositories.BeerRepository;
import com.casesr.spring6restmvc.repositories.CustomerRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Racquel.Cases
 */
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
  private final BeerRepository beerRepository;
  private final CustomerRepository customerRepository;

  @Override
  public void run(String... args) throws Exception {
    loadBeerData();
    loadCustomerData();
  }

  private void loadBeerData() {
    if (beerRepository.count() == 0) {
      Beer beer1 =
          Beer.builder()
              .beerName("Galaxy Cat")
              .beerStyle(BeerStyle.PALE_ALE)
              .upc("123456")
              .price(BigDecimal.valueOf(12.99))
              .quantityOnHand(122)
              .createdDate(LocalDateTime.now())
              .updateDate(LocalDateTime.now())
              .build();

      Beer beer2 =
          Beer.builder()
              .beerName("Crank")
              .beerStyle(BeerStyle.PALE_ALE)
              .upc("12345622")
              .price(BigDecimal.valueOf(11.99))
              .quantityOnHand(392)
              .createdDate(LocalDateTime.now())
              .updateDate(LocalDateTime.now())
              .build();

      Beer beer3 =
          Beer.builder()
              .beerName("Sunshine City")
              .beerStyle(BeerStyle.IPA)
              .upc("12356")
              .price(BigDecimal.valueOf(13.99))
              .quantityOnHand(144)
              .createdDate(LocalDateTime.now())
              .updateDate(LocalDateTime.now())
              .build();

      beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3));
    }
  }

  private void loadCustomerData() {
    if (customerRepository.count() == 0) {
      Customer customer1 =
          Customer.builder()
              .customerName("John Smith")
              .createdDate(LocalDateTime.now())
              .lastModifiedDate(LocalDateTime.now())
              .build();

      Customer customer2 =
          Customer.builder()
              .customerName("Juan dela Cruz")
              .createdDate(LocalDateTime.now())
              .lastModifiedDate(LocalDateTime.now())
              .build();

      customerRepository.saveAll(Arrays.asList(customer1, customer2));
    }
  }
}
