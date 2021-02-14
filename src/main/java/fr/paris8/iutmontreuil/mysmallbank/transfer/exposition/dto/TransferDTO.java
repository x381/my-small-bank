package fr.paris8.iutmontreuil.mysmallbank.transfer.exposition.dto;

import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AccountDTO;

import java.time.LocalDateTime;

public class TransferDTO {

    private String id;
    private String to;
    private String from;
    private double amount;
    private LocalDateTime executionDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(LocalDateTime executionDate) {
        this.executionDate = executionDate;
    }
}
