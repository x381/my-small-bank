package fr.paris8.iutmontreuil.mysmallbank.account.domain.model;

public class Address {

    private final String street;
    private final String zipCode;
    private final String city;
    private final String country;

    public Address(String street, String zipCode, String city, String country) {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public Address merge(Address address) {
        String street = this.street;
        String zipCode = this.zipCode;
        String city = this.city;
        String country = this.country;

        if(address.street != null && !address.street.isEmpty())
            street = address.street;

        if(address.zipCode != null && !address.zipCode.isEmpty())
            zipCode = address.zipCode;

        if(address.city != null && !address.city.isEmpty())
            city = address.city;

        if(address.country != null && !address.country.isEmpty())
            country = address.country;

        return new Address(street, zipCode, city, country);
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
