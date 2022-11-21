package br.com.doliver.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import br.com.doliver.domain.Person;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "person",
    indexes = {
        @Index(name = "person_pk", columnList = "idt_person", unique = true)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "person_uk01", columnNames = "cod_person")
    }
)
@NoArgsConstructor
public class PersonEntity implements Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idt_person", nullable = false, unique = true)
  private Long id;

  @Column(name = "cod_person", nullable = false)
  @Type(type = "uuid-char")
  private UUID code;

  @Column(name = "nam_person", nullable = false, length = 50)
  private String name;

  @CreationTimestamp
  @Column(name = "dat_creation", nullable = false)
  private LocalDateTime creationDate;

  @OneToMany(mappedBy = "person")
  private List<AccountEntity> accounts;

  @OneToMany(mappedBy = "person")
  private List<CreditCardEntity> creditCards;

  public PersonEntity(final Person person) {
    this.id = person.getId();
    this.code = person.getCode();
    this.name = person.getName();
    this.creationDate = person.getCreationDate();

    this.validate();
  }

  private void validate() {
    if (Objects.isNull(this.name) || this.name.isEmpty()) {
      throw new IllegalArgumentException("name can't be null or empty");
    }

    if (Objects.isNull(this.code)) {
      throw new IllegalArgumentException("code can't be null");
    }
  }
}

