package br.com.doliver.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.doliver.domain.enums.Category;

public interface Transaction {

  Long getId();

  LocalDateTime getDate();

  BigDecimal getAmount();

  Category getCategory();

  String getDescription();

  Account getAccount();

  CreditCard getCreditCard();
}
