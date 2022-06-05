package br.com.doliver.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {
  private Long id;
  private String alias;
  private Person person;
}
