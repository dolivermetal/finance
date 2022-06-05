package br.com.doliver.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCard {
  private Long id;
  private String alias;
  private String brand;
  private Person person;
}
