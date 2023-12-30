package pl.edu.pw.ee.bankbackend.api.account;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.bankbackend.api.account.data.AccountResponse;
import pl.edu.pw.ee.bankbackend.api.account.interfaces.AccountService;
import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;
import pl.edu.pw.ee.bankbackend.entities.account.Account;
import pl.edu.pw.ee.bankbackend.entities.account.interfaces.AccountMapper;
import pl.edu.pw.ee.bankbackend.entities.account.interfaces.AccountRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;
import pl.edu.pw.ee.bankbackend.exceptions.account.throwable.AccountDoesNotExistException;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final int BILL_NUMBER_LENGTH = 26;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public final AccountResponse getAccountDetails(User user) {
        log.info("Getting account details for user: {}", user);

        Account account = accountRepository.findByUser(user)
                .orElseThrow(() -> new AccountDoesNotExistException(HttpStatus.NOT_FOUND, "Account not found!"));

        log.info("Account details: {}", account);

        return accountMapper.mapToAccountResponse(account);
    }

    @Override
    @Transactional
    public void createNewAccount(RegisterRequest request, User user) {
        log.info("Creating new account for user: {}", user);
        log.info("Account request: {}", request);

        Account account = Account
                .builder()
                .accountType(request.accountType())
                .accountName(request.accountType().toAccountName())
                .balance(request.initialBalance())
                .user(user)
                .billNumber(generateNewUniqueBillNumber())
                .build();
        Account savedAccount = accountRepository.save(account);

        log.info("Created account: {}", savedAccount);
    }

    private String generateNewUniqueBillNumber() {
        String billNumber = generateBillNumber();

        while (accountRepository.existsByBillNumber(billNumber)) {
            billNumber = generateBillNumber();
        }
        return billNumber;
    }

    private String generateBillNumber() {
        return new SecureRandom(SecureRandom.getSeed(32))
                .ints(0, 9)
                .limit(BILL_NUMBER_LENGTH)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
