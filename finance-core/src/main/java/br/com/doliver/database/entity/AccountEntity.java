package br.com.doliver.database.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import br.com.doliver.domain.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"person"})
@Entity
@Table(name = "account",
    indexes = {
        @Index(name = "account_pk", columnList = "idt_account", unique = true)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "account_uk01", columnNames = "cod_account")
    }
)
@NoArgsConstructor
public class AccountEntity implements Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idt_account", nullable = false, unique = true)
    private Long id;

    @Column(name = "cod_account", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID code;

    @Column(name = "nam_alias", nullable = false, length = 50)
    private String alias;

    @ManyToOne
    @JoinColumn(name = "idt_person", nullable = false, foreignKey = @ForeignKey(name = "account_fk01"))
    private PersonEntity person;

    @CreationTimestamp
    @Column(name = "dat_creation", nullable = false)
    private LocalDateTime creationDate;

    public AccountEntity(final Account account, final PersonEntity person) {
        this.id = account.getId();
        this.code = account.getCode();
        this.alias = account.getAlias();
        this.creationDate = account.getCreationDate();
        this.person = person;

        this.validate();
    }

    private void validate() {
        if (Objects.isNull(this.alias) || this.alias.isEmpty()) {
            throw new IllegalArgumentException("alias can't be null or empty");
        }

        if (Objects.isNull(this.person)) {
            throw new IllegalArgumentException("Person can't be null");
        }

        if (Objects.isNull(this.code)) {
            throw new IllegalArgumentException("code can't be null or empty");
        }
    }
}
