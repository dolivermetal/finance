package br.com.doliver.database.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import lombok.Data;

@Data
@Entity
@Table(name = "outbox")
public class OutboxEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idt_outbox", nullable = false, unique = true)
  private Long id;

  @Column(name = "jsn_metadata", nullable = false)
  private String metadata;

  @Column(name = "jsn_metadata_text", nullable = false)
  private String metadataText;

  @Lob
  @Column(name = "jsn_metadata_byte", nullable = false)
  @Type(type = "org.hibernate.type.BinaryType")
  private byte[] metadataByte;

  @Column(name = "ind_integration_status", length = 1)
  private String integrationStatus;

  @CreationTimestamp
  @Column(name = "dat_creation", nullable = false)
  private LocalDateTime datCreation;

}
