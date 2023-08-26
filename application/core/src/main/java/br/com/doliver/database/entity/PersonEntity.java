package br.com.doliver.database.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.util.ObjectUtils;

import br.com.doliver.domain.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"accounts","creditCards"})
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

  @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
  private List<CreditCardEntity> creditCards;

  public PersonEntity(final Person person) {
    this.id = person.getId();
    this.code = person.getCode();
    this.name = person.getName();
    this.creationDate = person.getCreationDate();

    this.validate();
  }

  private void validate() {
    if (ObjectUtils.isEmpty(this.name)) {
      throw new IllegalArgumentException("name can't be null or empty");
    }

    if (Objects.isNull(this.code)) {
      throw new IllegalArgumentException("code can't be null");
    }
  }
}

