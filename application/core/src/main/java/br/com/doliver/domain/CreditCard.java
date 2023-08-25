package br.com.doliver.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Definição de um cartão de crédito.
 */
public interface CreditCard {

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
   * Apelido do cartão de crédito.
   *
   * @return String - apelido do cartão de crédito
   */
  String getAlias();

  /**
   * Bandeira do cartão.
   *
   * @return String - bandeira do cartão de crédito
   */
  String getBrand();

  /**
   * Pessoa dona do cartão de crédito.
   *
   * @return Person - pessoa qual o cartão de crédito está associado
   */
  Person getPerson();

  /**
   * Data de cadastro do cartão de crédito.
   *
   * @return LocalDateTime - data de cadastro do cartão de crédito
   */
  LocalDateTime getCreationDate();
}
