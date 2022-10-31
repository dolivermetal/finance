package br.com.doliver.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.doliver.domain.Person;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Table(name = "person")
@Entity
public class PersonEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idt_person", nullable = false, unique = true)
  private Long id;

  @Column(name = "cod_person", nullable = false, unique = true)
  private UUID code;

  @Column(name = "nam_person", nullable = false, length = 50)
  private String name;

  @CreationTimestamp
  @Column(name = "dat_creation", nullable = false)
  private LocalDateTime creationDate;

//  @OneToMany(mappedBy = "person")
//  private List<AccountEntity> accounts;
//
//  @OneToMany(mappedBy = "person")
//  private List<CreditCardEntity> creditCards;

  public PersonEntity(Person person) {
    this.code = person.getCode();
    this.name = person.getName();
    this.creationDate = person.getCreationDate();
  }
}
