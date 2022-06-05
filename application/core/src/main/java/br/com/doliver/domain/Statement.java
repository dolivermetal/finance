package br.com.doliver.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Statement {
  private Long id;
  private Account account;
  private CreditCard card;
  private LocalDateTime date;
  private BigDecimal balance;
  private Transaction transaction;
}
