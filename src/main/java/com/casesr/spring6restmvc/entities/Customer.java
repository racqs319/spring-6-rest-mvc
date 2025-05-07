package com.casesr.spring6restmvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

/**
 * @author Racquel.Cases
 */
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  @Id private UUID id;
  @Version private Integer version;
  private String customerName;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
}
