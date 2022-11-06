package br.com.doliver.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.doliver.domain.Transaction;
import br.com.doliver.domain.enums.Category;
import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
public class TransactionEntity implements Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idt_transaction")
  private Long id;

  @Column(name = "cod_transaction", nullable = false, unique = true)
  private UUID code;

  @Column(name = "dat_reference")
  private LocalDateTime referenceDate;

  @Column(name = "num_amount", scale = 19, precision = 2, nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(name = "ind_category", nullable = false)
  private Category category;

  @Column(name = "des_transaction")
  private String description;

  @CreationTimestamp
  @Column(name = "dat_creation", nullable = false)
  private LocalDateTime creationDate;

  @UpdateTimestamp
  @Column(name = "dat_update", nullable = false)
  private LocalDateTime updateDate;

  public TransactionEntity(final Transaction transaction) {
    this.id = transaction.getId();
    this.code = transaction.getCode();
    this.referenceDate = transaction.getReferenceDate();
    this.amount = transaction.getAmount();
    this.category = transaction.getCategory();
    this.description = transaction.getDescription();
    this.creationDate = transaction.getCreationDate();
    this.updateDate = transaction.getUpdateDate();
  }
}
