package fr.paris8.iutmontreuil.mysmallbank.account.exposition;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.AccountService;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountDTO> listAllAccounts() {
        return AccountMapper.toDTOs(accountService.listAllAccount());
    }

    @GetMapping("/{account-uid}")
    public AccountDTO getAccount(@PathVariable("account-uid") String accountUid) {
        return AccountMapper.toDTO(accountService.getAccount(accountUid));
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) throws URISyntaxException {
        Account createdAccount = accountService.createAccount(AccountMapper.toAccount(accountDTO));
        return ResponseEntity.created(new URI("/accounts/" + createdAccount.getUid())).body(AccountMapper.toDTO(createdAccount));
    }

    @PostMapping("/batch")
    public List<AccountDTO> createAccounts(@RequestBody List<AccountDTO> accountDTO) {
        List<Account> createdAccounts = accountService.createAccounts(AccountMapper.toAccount(accountDTO));
        return AccountMapper.toDTOs(createdAccounts);
    }

    @DeleteMapping("/{account-uid}")
    public AccountDTO deleteAccount(@PathVariable("account-uid") String accountUid, @RequestBody AccountDTO accountDTO) {
        Account deletedAccount = accountService.delete(accountUid, AccountMapper.toAccount(accountDTO));
        return AccountMapper.toDTO(deletedAccount);
    }

}
