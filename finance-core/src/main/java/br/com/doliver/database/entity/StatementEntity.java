package br.com.doliver.database.entity;

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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import br.com.doliver.domain.Statement;

@Data
@Entity
@Table(name = "statement",
    indexes = {
        @Index(name = "statement_pk", columnList = "idt_statement", unique = true)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "statement_uk01", columnNames = "cod_statement")
    }
)
@NoArgsConstructor
public class StatementEntity implements Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idt_statement")
    private Long id;

    @Column(name = "cod_statement", nullable = false)
    private UUID code;

    @ManyToOne
    @JoinColumn(name = "idt_account", foreignKey = @ForeignKey(name = "statement_fk01"))
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "idt_credit_card", foreignKey = @ForeignKey(name = "statement_fk03"))
    private CreditCardEntity creditCard;

    @ManyToOne
    @JoinColumn(name = "idt_transaction", nullable = false, foreignKey = @ForeignKey(name = "statement_fk02"))
    private TransactionEntity transaction;

    @Column(name = "num_balance")
    private BigDecimal balance;

    @Column(name = "dat_reference")
    private LocalDateTime referenceDate;

    @CreationTimestamp
    @Column(name = "dat_creation", nullable = false)
    private LocalDateTime creationDate;

    public StatementEntity(final Statement statement) {
        this.id = statement.getId();
        this.code = statement.getCode();
        this.account = (AccountEntity) statement.getAccount();
        this.creditCard = (CreditCardEntity) statement.getCreditCard();
        this.transaction = (TransactionEntity) statement.getTransaction();
        this.balance = statement.getBalance();
        this.referenceDate = statement.getReferenceDate();
        this.creationDate = statement.getCreationDate();
    }

}
