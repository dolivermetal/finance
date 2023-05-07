package br.com.doliver.database.postgres.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

import br.com.doliver.domain.Statement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "statement",
    indexes = {
        @Index(name = "statement_pk", columnList = "idt_statement", unique = true)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "statement_uk01", columnNames = "cod_statement")
    }
)
@NoArgsConstructor
public class StatementEntity implements Statement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idt_statement")
  private Long id;

  @Column(name = "cod_statement", nullable = false)
  private UUID code;

  @ManyToOne
  @JoinColumn(name = "idt_account", foreignKey = @ForeignKey(name = "statement_fk01"))
  private AccountEntity account;

  @ManyToOne
  @JoinColumn(name = "idt_credit_card", foreignKey = @ForeignKey(name = "statement_fk03"))
  private CreditCardEntity creditCard;

  @ManyToOne
  @JoinColumn(name = "idt_transaction", nullable = false, foreignKey = @ForeignKey(name = "statement_fk02"))
  private TransactionEntity transaction;

  @Column(name = "num_balance")
  private BigDecimal balance;

  @Column(name = "dat_reference")
  private LocalDateTime referenceDate;

  @CreationTimestamp
  @Column(name = "dat_creation", nullable = false)
  private LocalDateTime creationDate;

  public StatementEntity(final Statement statement) {
    this.id = statement.getId();
    this.code = statement.getCode();
    this.account = (AccountEntity) statement.getAccount();
    this.creditCard = (CreditCardEntity) statement.getCreditCard();
    this.transaction = (TransactionEntity) statement.getTransaction();
    this.balance = statement.getBalance();
    this.referenceDate = statement.getReferenceDate();
    this.creationDate = statement.getCreationDate();
  }

}
