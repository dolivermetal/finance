package br.com.doliver.database.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.ObjectUtils;

import br.com.doliver.domain.Referrer;
import br.com.doliver.domain.Transaction;
import br.com.doliver.domain.enums.Category;
import br.com.doliver.domain.event.ReferrerMessage;
import br.com.doliver.domain.event.TransactionMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "transaction",
    indexes = {
        @Index(name = "transaction_pk", columnList = "idt_transaction", unique = true)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "transaction_uk01", columnNames = "cod_transaction")
    }
)
@NoArgsConstructor
public class TransactionEntity implements Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idt_transaction")
  private Long id;

  @Column(name = "cod_transaction", nullable = false)
  private UUID code;

  @Column(name = "dat_reference")
  private LocalDateTime referenceDate;

  @Column(name = "num_amount", precision = 19, scale = 2, nullable = false)
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

  @Transient
  private Referrer referrer;

  public TransactionEntity(final Transaction transaction) {
    this.id = transaction.getId();
    this.code = transaction.getCode();
    this.referenceDate = transaction.getReferenceDate();
    this.amount = transaction.getAmount();
    this.category = transaction.getCategory();
    this.description = transaction.getDescription();
    this.creationDate = transaction.getCreationDate();
    this.updateDate = transaction.getUpdateDate();
    this.referrer = transaction.getReferrer();

    this.validate();
    this.generateDefaultValues();
  }

  private void validate() {

    if (Objects.isNull(this.amount) || this.amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("amount can't be null or equal zero");
    }

    if (ObjectUtils.isEmpty(this.description)) {
      throw new IllegalArgumentException("description can't be null or empty");
    }

    if (Objects.isNull(this.referrer)) {
      throw new IllegalArgumentException("referrer can't be null");
    }
  }

  private void generateDefaultValues() {
    if (Objects.isNull(this.code)) {
      this.code = UUID.randomUUID();
    }

    if (Objects.isNull(this.category)) {
      this.category = Category.OTHER;
    }
  }

  public TransactionMessage buildNewMessage() {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return TransactionMessage.newBuilder()
        .setCode(this.getCode().toString())
        .setReferenceDate(this.getReferenceDate().format(formatter))
        .setAmount(this.getAmount().doubleValue())
        .setCategory(this.getCategory().name())
        .setDescription(this.getDescription())
        .setCreationDate(this.getCreationDate().format(formatter))
        .setUpdateDate(this.getUpdateDate().format(formatter))
        .setReferrer(ReferrerMessage.newBuilder()
            .setType(this.getReferrer().type().name())
            .setCode(this.getReferrer().code().toString())
            .build())
        .build();
  }
}
