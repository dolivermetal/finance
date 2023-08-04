package br.com.doliver.domain;

import java.util.Objects;
import java.util.UUID;

import br.com.doliver.domain.enums.ReferrerType;

public record Referrer(ReferrerType type, UUID code) {

  public Referrer {
    Objects.requireNonNull(type);
    Objects.requireNonNull(code);
  }
}
