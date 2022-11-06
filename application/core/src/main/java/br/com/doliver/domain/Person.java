package br.com.doliver.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Definicação de uma pessoa
 */
public interface Person {

  /**
   * ID único de identificação
   */
  Long getId();

  /**
   * Código único de identificação
   */
  UUID getCode();

  /**
   * Nome da pessoa
   */
  String getName();

  /**
   * Data de cadastro da pessoa
   */
  LocalDateTime getCreationDate();

}
