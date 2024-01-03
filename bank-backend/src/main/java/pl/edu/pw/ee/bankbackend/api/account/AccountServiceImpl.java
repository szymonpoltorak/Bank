package pl.edu.pw.ee.bankbackend.api.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.bankbackend.api.account.data.AccountResponse;
import pl.edu.pw.ee.bankbackend.api.account.interfaces.AccountService;
import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;
import pl.edu.pw.ee.bankbackend.entities.account.BankAccount;
import pl.edu.pw.ee.bankbackend.entities.account.interfaces.AccountMapper;
import pl.edu.pw.ee.bankbackend.entities.account.interfaces.AccountRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;
import pl.edu.pw.ee.bankbackend.exceptions.account.throwable.AccountDoesNotExistException;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final long BILL_NUMBER_LENGTH = 26L;
    private static final int NUM_BYTES = 32;

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public final AccountResponse getAccountDetails(User user) {
        log.info("Getting account details for user: {}", user);

        BankAccount bankAccount = accountRepository.findByUser(user)
                .orElseThrow(() -> new AccountDoesNotExistException("Account not found!"));

        log.info("Account details: {}", bankAccount);

        return accountMapper.mapToAccountResponse(bankAccount);
    }

    @Override
    public final void createNewAccount(RegisterRequest request, User user) {
        log.info("Creating new account for user: {}", user);
        log.info("Account request: {}", request);

        BankAccount bankAccount = BankAccount
                .builder()
                .accountType(request.accountType())
                .accountName(request.accountType().toAccountName())
                .balance(request.initialBalance())
                .user(user)
                .billNumber(generateNewUniqueBillNumber())
                .build();
        BankAccount savedAccount = accountRepository.save(bankAccount);

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
        return new SecureRandom(SecureRandom.getSeed(NUM_BYTES))
                .ints(0, 9)
                .limit(BILL_NUMBER_LENGTH)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
