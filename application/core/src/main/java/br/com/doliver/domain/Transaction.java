package br.com.doliver.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.enums.Category;

public interface Transaction {

  Long getId();

  UUID getCode();

  LocalDateTime getReferenceDate();

  BigDecimal getAmount();

  Category getCategory();

  String getDescription();

  LocalDateTime getCreationDate();

  LocalDateTime getUpdateDate();

}
