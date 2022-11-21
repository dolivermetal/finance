package br.com.doliver.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface Statement {

  Long getId();

  UUID getCode();

  Account getAccount();

  CreditCard getCreditCard();

  LocalDateTime getReferenceDate();

  BigDecimal getBalance();

  Transaction getTransaction();

  LocalDateTime getCreationDate();

}
