package br.com.doliver.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

public interface Statement {

  Long getId();

  Account getAccount();

  CreditCard getCreditCard();

  LocalDateTime getRefenceDate();

  BigDecimal getBalance();

  Transaction getTransaction();

}
