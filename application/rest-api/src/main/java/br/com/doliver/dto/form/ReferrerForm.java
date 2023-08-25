package br.com.doliver.dto.form;

import java.util.UUID;

import br.com.doliver.domain.Referrer;
import br.com.doliver.domain.enums.ReferrerType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Formulário de um Referenciador para a criação de uma transação.
 */
@Getter
@Setter
@ToString
public class ReferrerForm {

  @NonNull
  private String type;

  @NonNull
  private String code;

  public Referrer asReferrer() {
    return new Referrer(ReferrerType.valueOf(this.type), UUID.fromString(this.code));
  }

}
