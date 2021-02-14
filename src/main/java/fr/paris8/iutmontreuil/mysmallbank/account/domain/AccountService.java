package fr.paris8.iutmontreuil.mysmallbank.account.domain;

import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.AccountType;
import fr.paris8.iutmontreuil.mysmallbank.common.ValidationError;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountRepository;
import fr.paris8.iutmontreuil.mysmallbank.common.exception.ValidationErrorException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> listAllAccount() {
        return accountRepository.listAllAccounts();
    }

    public Account getAccount(String accountUid) {
        return accountRepository.getAccount(accountUid);
    }

    public Account createAccount(Account account) {
        List<ValidationError> validationErrors = validateAccount(account);
        if (!validationErrors.isEmpty())
            throw new ValidationErrorException(validationErrors);

        return accountRepository.save(account);
    }

    public List<Account> createAccounts(List<Account> accounts) {
        return accounts.stream()
                .map(x -> {
                    List<ValidationError> validationErrors = validateAccount(x);
                    if (!validationErrors.isEmpty()) {
                        throw new ValidationErrorException(validationErrors);
                    }
                    return accountRepository.save(x);
                }).collect(Collectors.toList());
    }

    public Account delete(String accountUid, Account toAccount) {
        Account accountToDelete = getAccount(accountUid);
        Account accountToTransfer = getAccount(toAccount.getUid());

        List<ValidationError> validationErrors = validateDelete(accountToDelete, toAccount);
        if (!validationErrors.isEmpty())
            throw new ValidationErrorException(validationErrors);
        else
            accountRepository.save(accountToTransfer.updateBalance(accountToTransfer.getBalance() + accountToDelete.getBalance()));

        accountRepository.deleteTransfersOf(accountToDelete.getUid());

        return accountRepository.delete(accountToDelete);
    }

    private List<ValidationError> validateDelete(Account account, Account toAccount) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if (toAccount.getUid() == null || toAccount.getUid().isEmpty())
            validationErrors.add(new ValidationError("account : transfer is not defined or empty - you need to transfer the balance of this account to an other account before deleting it."));
        else {
            if (!accountRepository.accountExists(toAccount.getUid()))
                validationErrors.add(new ValidationError("account : id = " + toAccount.getUid() + " does not exist."));
        }

        if (account.getBalance() < 0)
            validationErrors.add(new ValidationError("account : balance is negative."));

        return validationErrors;
    }

    private List<ValidationError> validateAccount(Account account) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if (account.getHolder() == null)
            validationErrors.add(new ValidationError("account : holder is not defined."));
        else {
            if (account.getHolder().getId().isEmpty())
                validationErrors.add(new ValidationError("account : holder - id is empty."));
            else {
                if (!accountRepository.holderExists(account.getHolder().getId()))
                    validationErrors.add(new ValidationError("account : holder - id = " + account.getHolder().getId() + " does not exist."));
            }
        }

        if (account.getCategory() == null)
            validationErrors.add(new ValidationError("account : type is not defined."));
        else {
            if (account.getCategory() == AccountType.PEL && account.getBalance() < AccountType.PEL.getMinimumBalance())
                validationErrors.add(new ValidationError("type & balance : balance too low for a PEL category."));
        }

        if (account.getBalance() < 0)
            validationErrors.add(new ValidationError("balance : negative balance not allowed."));

        return validationErrors;
    }

}
