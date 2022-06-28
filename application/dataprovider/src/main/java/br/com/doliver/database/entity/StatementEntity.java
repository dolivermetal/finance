package br.com.doliver.database.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

import lombok.Data;

@Data
@Entity
@Table(name = "statement")
public class StatementEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idt_statement")
  private Long id;

  @Column(name = "cod_settlement", nullable = false, unique = true)
  private UUID code;

  @ManyToOne
  @JoinColumn(name = "idt_account")
  private AccountEntity account;

  @ManyToOne
  @JoinColumn(name = "idt_credit_card")
  private CreditCardEntity creditCard;

  @ManyToOne
  @JoinColumn(name = "idt_transaction", nullable = false)
  private TransactionEntity transaction;

  @Column(name = "num_balance")
  private BigDecimal balance;

  @Column(name = "dat_reference")
  private LocalDateTime referenceDate;

  @CreationTimestamp
  @Column(name = "dat_creation", nullable = false)
  private LocalDateTime creationDate;

}
