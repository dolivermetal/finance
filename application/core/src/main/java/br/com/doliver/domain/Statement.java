package br.com.doliver.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Definição de um registro de extrato.
 */
public interface Statement {

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
   * Conta qual o registro de extrato está associado.
   *
   * @return Account - conta qual o registro de extrato está associado.
   */
  Account getAccount();

  /**
   * Cartão de cŕedito qual o registro de extrato está associado.
   *
   * @return CreditCard - cartão de crédito qual o registro de extrato está associado.
   */
  CreditCard getCreditCard();

  /**
   * Data de ocorrência do registro no extrato.
   *
   * @return LocalDateTime - data de ocorrência do registro no extrato
   */
  LocalDateTime getReferenceDate();

  /**
   * Saldo do extrato após a inclusão do registro.
   *
   * @return LocalDateTime - saldo do extrato após a inclusão do registro
   */
  BigDecimal getBalance();

  /**
   * Transação que originou o registro de extrato.
   *
   * @return Transaction - transação que originou o registro de extrato
   */
  Transaction getTransaction();

  /**
   * Data de cadastro do registro no extrato.
   *
   * @return LocalDateTime - data de cadastro do registro no extrato
   */
  LocalDateTime getCreationDate();

}
