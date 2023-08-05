package br.com.doliver.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.enums.Category;

/**
 * Definição de uma transação.
 */
public interface Transaction {

  /**
   * ID único de identificação.
   *
   * @return Long - id único de identificação
   */
  Long getId();

  /**
   * Código único de identificação.
   *
   * @return UUID - código único de identificação
   */
  UUID getCode();

  /**
   * Data de ocorrência da transação.
   *
   * @return LocalDateTime - data de ocorrência da transação
   */
  LocalDateTime getReferenceDate();

  /**
   * Valor da transação.
   *
   * @return BigDecimal - valor da transação
   */
  BigDecimal getAmount();

  /**
   * Categoria da transação.
   *
   * @return Category - categoria da transação (BILLS, HOME, AUTOMOBILE, ...)
   */
  Category getCategory();

  /**
   * Descriçõo da transação.
   *
   * @return String - descrição da transação
   */
  String getDescription();

  /**
   * Data de cadastro da transação.
   *
   * @return LocalDateTime - data de cadastro da transação
   */
  LocalDateTime getCreationDate();

  /**
   * Data de atualização da transação.
   *
   * @return LocalDateTime - data de atualização da transação
   */
  LocalDateTime getUpdateDate();

  /**
   * Referenciador da transação.
   *
   * @return Referrer - referenciador da transação
   */
  Referrer getReferrer();

}
