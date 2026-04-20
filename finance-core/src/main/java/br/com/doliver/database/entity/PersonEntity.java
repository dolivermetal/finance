package br.com.doliver.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import br.com.doliver.domain.Person;

@Getter
@Setter
@ToString(exclude = {"accounts", "creditCards"})
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
    @JdbcTypeCode(SqlTypes.VARCHAR)
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
        if (Objects.isNull(this.name) || this.name.isEmpty()) {
            throw new IllegalArgumentException("name can't be null or empty");
        }

        if (Objects.isNull(this.code)) {
            throw new IllegalArgumentException("code can't be null");
        }
    }
}

