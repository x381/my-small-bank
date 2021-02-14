package fr.paris8.iutmontreuil.mysmallbank.account.infrastructure;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.transfer.infrastructure.TransferDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AccountRepository {

    private final AccountDAO accountDAO;
    private final HolderDAO holderDAO;
    private final TransferDAO transferDAO;

    public AccountRepository(AccountDAO accountDAO, HolderDAO holderDAO, TransferDAO transferDAO) {
        this.accountDAO = accountDAO;
        this.holderDAO = holderDAO;
        this.transferDAO = transferDAO;
    }

    public Account getAccount(String uid) {
        return AccountMapper.toAccount(accountDAO.getOne(uid));
    }

    public List<Account> listAllAccounts() {
        return accountDAO.findAll().stream()
                .map(AccountMapper::toAccount)
                .collect(Collectors.toList());
    }

    public boolean holderExists(String id) {
        return holderDAO.existsById(id);
    }

    public boolean accountExists(String id) {
        return accountDAO.existsById(id);
    }

    public Account save(Account account) {
        AccountEntity entityToSave = AccountMapper.toEntity(account);
        return AccountMapper.toAccount(accountDAO.save(entityToSave));
    }

    public void deleteTransfersOf(String accountId) {
        transferDAO.deleteTransfersOf(accountId);
    }

    public Account delete(Account account) {
        AccountEntity entityToDelete = AccountMapper.toEntity(account);
        Account deletedAccount = AccountMapper.toAccount(entityToDelete);
        accountDAO.delete(entityToDelete);
        return deletedAccount;
    }
}
