package fr.paris8.iutmontreuil.mysmallbank.account.domain.model;

public class Account {

    private final String uid;
    private final Holder holder;

    private final AccountType type;
    private final double balance;

    public Account(String uid, Holder holder, AccountType type, double balance) {
        this.uid = uid;
        this.holder = holder;
        this.type = type;
        this.balance = balance;
    }

    public Account updateBalance(double balance) {
        return new Account(this.uid, this.holder, this.type, balance);
    }

    public String getUid() {
        return uid;
    }

    public Holder getHolder() {
        return holder;
    }

    public AccountType getCategory() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

}
