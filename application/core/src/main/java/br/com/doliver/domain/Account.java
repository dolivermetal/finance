package br.com.doliver.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Definição de uma conta.
 */
public interface Account {

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
   * Apelido da conta.
   *
   * @return String - apelido da conta
   */
  String getAlias();

  /**
   * Pessoa dona da conta.
   *
   * @return Person - pessoa qual a conta está associada
   */
  Person getPerson();

  /**
   * Data de cadastro da conta.
   *
   * @return LocalDateTime - data de cadastro da conta
   */
  LocalDateTime getCreationDate();

}
