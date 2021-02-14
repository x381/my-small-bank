package fr.paris8.iutmontreuil.mysmallbank.transfer.domain;

import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.common.ValidationError;
import fr.paris8.iutmontreuil.mysmallbank.common.exception.ValidationErrorException;
import fr.paris8.iutmontreuil.mysmallbank.transfer.domain.model.Order;
import fr.paris8.iutmontreuil.mysmallbank.transfer.infrastructure.TransferRepository;
import fr.paris8.iutmontreuil.mysmallbank.transfer.domain.model.Transfer;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransferService {

    private final TransferRepository transferRepository;

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public List<Transfer> listAllTransfers(Order order) {
        Sort sort = Sort.by(Sort.Direction.ASC, "executionDate");
        if(order.equals(Order.DESC))
            sort = Sort.by(Sort.Direction.DESC,"executionDate");

        return transferRepository.listAllTransfers(sort);
    }

    public Transfer createTransfer(Transfer transfer) {
        List<ValidationError> validationErrors = validateTransfer(transfer);
        if (!validationErrors.isEmpty())
            throw new ValidationErrorException(validationErrors);
        else {
            Account sender = transferRepository.getAccount(transfer.getAccountIdFrom());
            Account receiver = transferRepository.getAccount(transfer.getAccountIdTo());
            transferRepository.saveAccountsFromTransfer(sender.updateBalance(sender.getBalance() - transfer.getAmount()), receiver.updateBalance(receiver.getBalance() + transfer.getAmount()));
        }

        return transferRepository.save(transfer);
    }

    public List<ValidationError> validateTransfer(Transfer transfer) {
        List<ValidationError> validationErrors = new ArrayList<>();
        Account accountFrom;

        if (transfer.getAccountIdFrom() == null || transfer.getAccountIdFrom().isEmpty())
            validationErrors.add(new ValidationError("transfer : from is not defined or empty."));
        else {
            if (!transferRepository.exists(transfer.getAccountIdFrom()))
                validationErrors.add(new ValidationError("transfer : id - from = " + transfer.getAccountIdFrom() + " does not exist."));
            else {
                accountFrom = transferRepository.getAccount(transfer.getAccountIdFrom());

                if (accountFrom.getBalance() - transfer.getAmount() < accountFrom.getCategory().getMinimumBalance())
                    validationErrors.add(new ValidationError("transfer : your balance is too low on account-id = " + accountFrom.getUid() + " to transfer."));
            }
        }

        if (transfer.getAccountIdTo() == null || transfer.getAccountIdTo().isEmpty())
            validationErrors.add(new ValidationError("transfer : to is not defined or empty."));
        else {
            if (!transferRepository.exists(transfer.getAccountIdTo()))
                validationErrors.add(new ValidationError("transfer : id - to = " + transfer.getAccountIdTo() + " does not exist."));
            else {
                if (transfer.getAccountIdFrom().equals(transfer.getAccountIdTo())) {
                    validationErrors.add(new ValidationError("transfer : you can't transfer from an account to the same account."));
                    return validationErrors;
                }
            }
        }

        if (transfer.getAmount() < 1)
            validationErrors.add(new ValidationError("transfer : transfer minimum amount is 1€."));

        return validationErrors;
    }

}
