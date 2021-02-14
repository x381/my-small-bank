package fr.paris8.iutmontreuil.mysmallbank.account;

import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Address;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AccountDTO;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AddressDTO;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.HolderDTO;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountEntity;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.HolderEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HolderMapper {

    private HolderMapper() {
    }

    public static Holder toHolder(HolderEntity entity) {
        return new Holder(entity.getId(), entity.getLastName(), entity.getFirstName(), new Address(entity.getStreet(), entity.getZipCode(), entity.getCity(), entity.getCountry()), entity.getBirthDate(), toAccount(entity.getAccounts()));
    }

    public static Holder toHolder(HolderDTO holderDTO) {
        return new Holder(null, holderDTO.getLastName(), holderDTO.getFirstName(), toAddress(holderDTO.getAddress()), holderDTO.getBirthDate(), Collections.emptyList());
    }

    public static HolderEntity toEntity(Holder holder) {
        HolderEntity entity = new HolderEntity();
        entity.setBirthDate(holder.getBirthDate());
        entity.setFirstName(holder.getFirstName());
        entity.setLastName(holder.getLastName());
        entity.setId(holder.getId());
        entity.setStreet(holder.getAddress().getStreet());
        entity.setZipCode(holder.getAddress().getZipCode());
        entity.setCity(holder.getAddress().getCity());
        entity.setCountry(holder.getAddress().getCountry());

        return entity;
    }

    public static HolderDTO toDTO(Holder holder) {
        HolderDTO holderDTO = new HolderDTO();
        holderDTO.setId(holder.getId());
        holderDTO.setLastName(holder.getLastName());
        holderDTO.setFirstName(holder.getFirstName());
        holderDTO.setBirthDate(holder.getBirthDate());
        holderDTO.setAccounts(toAccounts(holder.getAccounts()));
        holderDTO.setAddress(toAddressDTO(holder.getAddress()));

        return holderDTO;
    }

    public static List<HolderDTO> toDTOs(List<Holder> holders) {
        return holders.stream()
                .map(HolderMapper::toDTO)
                .collect(Collectors.toList());
    }

    private static Account toAccount(AccountEntity entity) {
        return new Account(entity.getUid(), null, entity.getAccountType(), entity.getBalance());
    }

    private static AccountDTO toDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance(account.getBalance());
        accountDTO.setType(account.getCategory());
        accountDTO.setUid(account.getUid());
        return accountDTO;
    }

    public static Address toAddress(AddressDTO addressDTO) {
        if (addressDTO == null)
            return null;
        return new Address(addressDTO.getStreet(), addressDTO.getZipCode(), addressDTO.getCity(), addressDTO.getCountry());
    }

    public static AddressDTO toAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(address.getStreet());
        addressDTO.setZipCode(address.getZipCode());
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());
        return addressDTO;
    }

    private static List<AccountDTO> toAccounts(List<Account> accounts) {
        if (accounts == null) {
            return Collections.emptyList();
        }

        return accounts.stream()
                .map(HolderMapper::toDTO)
                .collect(Collectors.toList());
    }

    private static List<Account> toAccount(List<AccountEntity> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(HolderMapper::toAccount)
                .collect(Collectors.toList());
    }

}
