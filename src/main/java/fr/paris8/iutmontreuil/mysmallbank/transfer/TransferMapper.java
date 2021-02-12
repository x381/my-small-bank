package fr.paris8.iutmontreuil.mysmallbank.transfer;

import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AccountDTO;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountEntity;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.HolderEntity;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.TransferEntity;

import java.util.List;
import java.util.stream.Collectors;

public class TransferMapper {

    private TransferMapper() {

    }

    public static Transfer toTransfer(TransferDTO transferDTO) {
        return new Transfer(transferDTO.getId(), transferDTO.getFrom(), transferDTO.getTo(), transferDTO.getAmount());
    }

    public static Transfer toTransfer(TransferEntity transferEntity) {
        return new Transfer(transferEntity.getUid(), transferEntity.getFrom().getUid(), transferEntity.getTo().getUid(), transferEntity.getAmount());
    }

    public static TransferDTO toDTO(Transfer transfer) {
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setId(transfer.getId());
        transferDTO.setFrom(transfer.getAccountIdFrom());
        transferDTO.setTo(transfer.getAccountIdTo());
        transferDTO.setAmount(transfer.getAmount());
        transferDTO.setExecutionDate(transferDTO.getExecutionDate());
        return transferDTO;
    }

    public static TransferEntity toEntity(Transfer transfer) {
        TransferEntity transferEntity = new TransferEntity();
        transferEntity.setUid(transfer.getId());
        transferEntity.setFrom(toAccountEntity(transfer.getAccountIdFrom()));
        transferEntity.setTo(toAccountEntity(transfer.getAccountIdTo()));
        transferEntity.setAmount(transfer.getAmount());
        transferEntity.setExecutionDate(transfer.getExecutionDate());

        return transferEntity;
    }

    public static Account toAccount(AccountEntity accountEntity) {
        return new Account(accountEntity.getUid(), toHolder(accountEntity.getHolder()), accountEntity.getAccountType(), accountEntity.getBalance());
    }

    public static AccountEntity toAccountEntity(String id) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUid(id);
        return accountEntity;
    }

    private static HolderEntity toHolderEntity(Holder holder) {
        HolderEntity holderEntity = new HolderEntity();
        holderEntity.setId(holder.getId());
        return holderEntity;
    }

    public static Holder toHolder(HolderEntity transferEntity) {
        return new Holder(transferEntity.getId(), null, null, null, null, null);
    }

    public static AccountEntity toAccountEntity(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUid(account.getUid());
        accountEntity.setHolder(toHolderEntity(account.getHolder()));
        accountEntity.setAccountType(account.getCategory());
        accountEntity.setBalance(account.getBalance());

        return accountEntity;
    }

    public static List<AccountEntity> toAccountEntities(List<Account> accounts) {
        return accounts.stream()
                .map(TransferMapper::toAccountEntity)
                .collect(Collectors.toList());
    }

    public static List<TransferDTO> toDTOs(List<Transfer> transfers) {
        return transfers.stream()
                .map(TransferMapper::toDTO)
                .collect(Collectors.toList());
    }
}
