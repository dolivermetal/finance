package br.com.doliver.domain;

import lombok.Data;

@Data
public class CreditCard {
  private Long id;
  private String alias;
  private Person person;
  private String brand;
}
