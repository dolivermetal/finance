package br.com.doliver.database.entity;

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
@Table(name = "credit_card")
public class CreditCardEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idt_credit_card", nullable = false, unique = true)
  private Long id;

  @Column(name = "cod_credit_card", nullable = false, unique = true)
  private UUID code;

  @Column(name = "nam_alias", nullable = false, length = 50)
  private String alias;

  @Column(name = "nam_brand", length = 30)
  private String brand;

  @ManyToOne
  @JoinColumn(name = "idt_person", nullable = false)
  private PersonEntity person;

  @CreationTimestamp
  @Column(name = "dat_creation", nullable = false)
  private LocalDateTime creationDate;

}
