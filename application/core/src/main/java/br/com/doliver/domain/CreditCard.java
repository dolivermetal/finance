package br.com.doliver.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CreditCard {

  Long getId();

  UUID getCode();

  String getAlias();

  String getBrand();

  Person getPerson();

  LocalDateTime getCreationDate();
}
