package br.com.doliver.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Definição de uma conta
 */
public interface Account {

  /**
   * ID único de identificação
   */
  Long getId();

  /**
   * Código único de identificação
   */
  UUID getCode();

  /**
   * Apelido da conta
   */
  String getAlias();

  /**
   * Pessoa dona da conta
   * @return Person
   */
  Person getPerson();

  /**
   * Data de cadastro da conta
   */
  LocalDateTime getCreationDate();

}
