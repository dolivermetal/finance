package br.com.doliver.domain;

import lombok.Data;

@Data
public class Account {
  private Long id;
  private String alias;
  private Person person;
}
