package br.com.doliver.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.doliver.domain.enumeration.Category;
import lombok.Data;

@Data
public class Transaction {
  private Long id;
  private LocalDateTime date;
  private BigDecimal amount;
  private Category category;

}
