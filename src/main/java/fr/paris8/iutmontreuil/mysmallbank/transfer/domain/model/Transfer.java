package fr.paris8.iutmontreuil.mysmallbank.transfer.domain.model;

import java.time.LocalDateTime;

public class Transfer {

    private final String id;
    private final String accountIdFrom;
    private final String accountIdTo;
    private final double amount;
    private final LocalDateTime executionDate;

    public Transfer(String id, String accountIdFrom, String accountIdTo, double amount) {
        this.id = id;
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.amount = amount;
        this.executionDate = LocalDateTime.now();
    }

    public Transfer(String id, String accountIdFrom, String accountIdTo, double amount, LocalDateTime executionDate) {
        this.id = id;
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.amount = amount;
        this.executionDate = executionDate;
    }

    public String getId() {
        return id;
    }

    public String getAccountIdFrom() {
        return accountIdFrom;
    }

    public String getAccountIdTo() {
        return accountIdTo;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

}
