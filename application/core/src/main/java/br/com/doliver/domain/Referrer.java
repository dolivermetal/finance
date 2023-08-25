package br.com.doliver.domain;

import java.util.Objects;
import java.util.UUID;

import br.com.doliver.domain.enums.ReferrerType;

/**
 * Referenciador para a criação de uma transação.
 *
 * @param type - tipo do referenciador, podendo ser ACCOUNT ou CREDIT_CARD
 * @param code - código único de identificação do referenciador utilizado na transação
 */
public record Referrer(ReferrerType type, UUID code) {

  /**
   * Referenciador para a criação de uma transação.
   *
   * @param type - tipo do referenciador, podendo ser ACCOUNT ou CREDIT_CARD
   * @param code - código único de identificação do referenciador utilizado na transação
   */
  public Referrer {
    Objects.requireNonNull(type);
    Objects.requireNonNull(code);
  }
}
