package fr.paris8.iutmontreuil.mysmallbank.transfer.infrastructure;

import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
public class TransferEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    @JoinColumn(name = "account_from", nullable = false)
    private AccountEntity from;

    @ManyToOne
    @JoinColumn(name = "account_to", nullable = false)
    private AccountEntity to;

    @Column(name = "amount")
    private double amount;

    @Column(name = "execution_date")
    private LocalDateTime executionDate;

    public void setUid(String uid) {
        this.id = uid;
    }

    public String getUid() {
        return id;
    }

    public void setFrom(AccountEntity from) {
        this.from = from;
    }

    public AccountEntity getFrom() {
        return from;
    }

    public void setTo(AccountEntity to) {
        this.to = to;
    }

    public AccountEntity getTo() {
        return to;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setExecutionDate(LocalDateTime executionDate) {
        this.executionDate = executionDate;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }
}
