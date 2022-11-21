package br.com.doliver.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;

import br.com.doliver.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "account",
    indexes = {
        @Index(name = "account_pk", columnList = "idt_account", unique = true)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "account_uk01", columnNames = "cod_account")
    }
)
@NoArgsConstructor
public class AccountEntity implements Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idt_account", nullable = false, unique = true)
  private Long id;

  @Column(name = "cod_account", nullable = false)
  private UUID code;

  @Column(name = "nam_alias", nullable = false, length = 50)
  private String alias;

  @ManyToOne
  @JoinColumn(name = "idt_person", nullable = false, foreignKey = @ForeignKey(name = "account_fk01"))
  private PersonEntity person;

  @CreationTimestamp
  @Column(name = "dat_creation", nullable = false)
  private LocalDateTime creationDate;

  public AccountEntity(final Account account, final PersonEntity person) {
    this.id = account.getId();
    this.code = account.getCode();
    this.alias = account.getAlias();
    this.creationDate = account.getCreationDate();
    this.person = person;

    this.validate();
  }

  private void validate() {
    if (Objects.isNull(this.alias) || this.alias.isEmpty()) {
      throw new IllegalArgumentException("alias can't be null or empty");
    }

    if (Objects.isNull(this.person)) {
      throw new IllegalArgumentException("Person can't be null");
    }
  }
}
