package com.casesr.spring6restmvc.entities;

import com.casesr.spring6restmvc.model.BeerStyle;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
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

  @Id
  @GeneratedValue(generator = "UUID")
  @UuidGenerator
  @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
  private UUID id;

  @Version private Integer version;
  private String beerName;
  private BeerStyle beerStyle;
  private String upc;
  private Integer quantityOnHand;
  private BigDecimal price;
  private LocalDateTime createdDate;
  private LocalDateTime updateDate;
}
