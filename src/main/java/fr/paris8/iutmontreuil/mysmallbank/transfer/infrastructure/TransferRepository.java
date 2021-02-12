package fr.paris8.iutmontreuil.mysmallbank.transfer;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountDAO;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountEntity;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.TransferDAO;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.TransferEntity;
import org.springframework.stereotype.Repository;

@Repository
public class TransferRepository {

    private final TransferDAO transferDAO;
    private final AccountDAO accountDAO;

    public TransferRepository(TransferDAO transferDAO, AccountDAO accountDAO) {
        this.transferDAO = transferDAO;
        this.accountDAO = accountDAO;
    }

    public Account getAccount(String id) {
        return TransferMapper.toAccount(accountDAO.getOne(id));
    }

    public boolean exists(String id) {
        return accountDAO.existsById(id);
    }

    public Transfer save(Transfer transfer) {
        TransferEntity entityToSave = TransferMapper.toEntity(transfer);
        return TransferMapper.toTransfer(transferDAO.save(entityToSave));
    }

    public void saveAccountFromTransfer(Account sender, Account receiver) {
        AccountEntity entityToSave1 = TransferMapper.toAccountEntity(sender);
        AccountEntity entityToSave2 = TransferMapper.toAccountEntity(receiver);
        accountDAO.save(entityToSave1);
        accountDAO.save(entityToSave2);
    }
}
