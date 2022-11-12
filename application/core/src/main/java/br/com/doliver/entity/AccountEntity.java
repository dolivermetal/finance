package br.com.doliver.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import br.com.doliver.domain.Account;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class AccountEntity implements Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idt_account", nullable = false, unique = true)
  private Long id;

  @Column(name = "cod_account", nullable = false, unique = true)
  private UUID code;

  @Column(name = "nam_alias", nullable = false, length = 50)
  private String alias;

  @ManyToOne
  @JoinColumn(name = "idt_person", nullable = false)
  private PersonEntity person;

  @CreationTimestamp
  @Column(name = "dat_creation", nullable = false)
  private LocalDateTime creationDate;

  public AccountEntity(final Account account) {
    this.id = account.getId();
    this.code = account.getCode();
    this.alias = account.getAlias();
    this.person = new PersonEntity(account.getPerson());
    this.creationDate = account.getCreationDate();

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
