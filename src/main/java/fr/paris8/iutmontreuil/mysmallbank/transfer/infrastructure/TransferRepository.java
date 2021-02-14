package fr.paris8.iutmontreuil.mysmallbank.transfer.infrastructure;

import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountDAO;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountEntity;
import fr.paris8.iutmontreuil.mysmallbank.transfer.TransferMapper;
import fr.paris8.iutmontreuil.mysmallbank.transfer.domain.model.Transfer;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransferRepository {

    private final TransferDAO transferDAO;
    private final AccountDAO accountDAO;

    public TransferRepository(TransferDAO transferDAO, AccountDAO accountDAO) {
        this.transferDAO = transferDAO;
        this.accountDAO = accountDAO;
    }

    public List<Transfer> listAllTransfers(Sort sort) {
        return TransferMapper.toTransfers(transferDAO.findAll(sort));
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

    public void saveAccountsFromTransfer(Account accountFrom, Account accountTo) {
        AccountEntity entityToSave1 = TransferMapper.toAccountEntity(accountFrom);
        AccountEntity entityToSave2 = TransferMapper.toAccountEntity(accountTo);
        accountDAO.save(entityToSave1);
        accountDAO.save(entityToSave2);
    }
}
