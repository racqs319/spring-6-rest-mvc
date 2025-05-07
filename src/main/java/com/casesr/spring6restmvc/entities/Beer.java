package com.casesr.spring6restmvc.entities;

import com.casesr.spring6restmvc.model.BeerStyle;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import org.springframework.stereotype.Service;

/**
 * @author Racquel.Cases
 */
@Getter
@Service
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Beer {

  @Id private UUID id;
  @Version private Integer version;
  private String beerName;
  private BeerStyle beerStyle;
  private String upc;
  private Integer quantityOnHand;
  private BigDecimal price;
  private LocalDateTime createdDate;
  private LocalDateTime updateDate;
}
