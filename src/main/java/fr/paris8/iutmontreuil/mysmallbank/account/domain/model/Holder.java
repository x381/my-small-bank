package fr.paris8.iutmontreuil.mysmallbank.account.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Holder {

    private final String id;
    private final String lastName;
    private final String firstName;
    private final Address address;
    private final LocalDate birthDate;

    private final List<Account> accounts;


    public Holder(String id, String lastName, String firstName, Address address, LocalDate birthDate, List<Account> accounts) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.birthDate = birthDate;
        this.accounts = accounts;
    }

    public Holder updateAddress(Address address) {
        return new Holder(this.id, this.lastName, this.firstName, address, this.birthDate, this.accounts);
    }

    public Holder merge(Holder holder) {
        String firstName = this.firstName;
        String lastName = this.lastName;
        LocalDate birthDate = this.birthDate;
        Address address = this.address;

        if(holder.firstName != null && !holder.firstName.isEmpty())
            firstName = holder.firstName;

        if(holder.lastName != null && !holder.lastName.isEmpty())
            lastName = holder.lastName;

        if(holder.birthDate != null && !holder.birthDate.toString().isEmpty())
            birthDate = holder.birthDate;

        if(holder.address != null)
            address = address.merge(holder.address);

        return new Holder(this.id, lastName, firstName, address, birthDate, this.accounts);
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
