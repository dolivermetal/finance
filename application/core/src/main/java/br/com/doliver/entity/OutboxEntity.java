package br.com.doliver.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.doliver.domain.Outbox;
import lombok.Data;

@Data
@Entity
@Table(name = "outbox")
public class OutboxEntity implements Outbox {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idt_outbox", nullable = false, unique = true)
  private Long id;

  @Column(name = "cod_outbox", nullable = false, unique = true)
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
  }
}
