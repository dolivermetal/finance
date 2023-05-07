package br.com.doliver.database.postgres.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.doliver.domain.Outbox;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "outbox",
    indexes = {
        @Index(name = "outbox_pk", columnList = "idt_outbox", unique = true)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "outbox_uk01", columnNames = "cod_outbox")
    }
)
@NoArgsConstructor
public class OutboxEntity implements Outbox {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idt_outbox", nullable = false, unique = true)
  private Long id;

  @Column(name = "cod_outbox", nullable = false)
  private UUID code;

  @Column(name = "nam_topic", nullable = false)
  private String topic;

  @Column(name = "jsn_metadata", nullable = false)
  private String metadata;

  @Column(name = "ind_integration_status", length = 1)
  private String integrationStatus;

  @CreationTimestamp
  @Column(name = "dat_creation", nullable = false)
  private LocalDateTime creationDate;

  @UpdateTimestamp
  @Column(name = "dat_update", nullable = false)
  private LocalDateTime updateDate;

  public OutboxEntity(final Outbox outbox) {
    this.id = outbox.getId();
    this.code = outbox.getCode();
    this.topic = outbox.getTopic();
    this.metadata = outbox.getMetadata();
    this.integrationStatus = outbox.getIntegrationStatus();
    this.creationDate = outbox.getCreationDate();
    this.updateDate = outbox.getUpdateDate();

    this.validate();
    this.generateDefaultValues();
  }

  public OutboxEntity(final TransactionEntity transaction) {
    this.topic = "br.com.doliver.finance.transactions";
    this.metadata = transaction.toString();

    this.validate();
    this.generateDefaultValues();
  }

  private void validate() {
    if (Objects.isNull(this.topic) || this.topic.isEmpty()) {
      throw new IllegalArgumentException("topic name can't be null or empty");
    }

    if (Objects.isNull(this.metadata) || this.metadata.isEmpty()) {
      throw new IllegalArgumentException("metadata can't be null or empty");
    }
  }

  private void generateDefaultValues() {
    if (Objects.isNull(this.code)) {
      this.code = UUID.randomUUID();
    }

    if (Objects.isNull(this.integrationStatus)) {
      this.integrationStatus = "W";
    }
  }
}
