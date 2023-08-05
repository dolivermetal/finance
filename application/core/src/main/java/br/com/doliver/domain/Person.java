package br.com.doliver.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Definicação de uma pessoa.
 */
public interface Person {

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
   * Nome da pessoa.
   *
   * @return String - nome da pessoa
   */
  String getName();

  /**
   * Data de cadastro da pessoa.
   *
   * @return LocalDateTime - data de cadastro da pessoa
   */
  LocalDateTime getCreationDate();

}
