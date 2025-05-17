package com.casesr.spring6restmvc.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

/**
 * @author Racquel.Cases
 */
@Data
@Builder
public class CustomerDTO {
  private UUID id;
  private String customerName;
  private Integer version;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
}
