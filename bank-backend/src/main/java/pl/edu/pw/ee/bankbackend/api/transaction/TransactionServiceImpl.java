package pl.edu.pw.ee.bankbackend.api.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.bankbackend.api.transaction.data.TransactionRequest;
import pl.edu.pw.ee.bankbackend.api.transaction.data.TransactionResponse;
import pl.edu.pw.ee.bankbackend.api.transaction.interfaces.TransactionService;
import pl.edu.pw.ee.bankbackend.entities.account.BankAccount;
import pl.edu.pw.ee.bankbackend.entities.account.interfaces.AccountRepository;
import pl.edu.pw.ee.bankbackend.entities.transaction.Transaction;
import pl.edu.pw.ee.bankbackend.entities.transaction.interfaces.TransactionMapper;
import pl.edu.pw.ee.bankbackend.entities.transaction.interfaces.TransactionRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;
import pl.edu.pw.ee.bankbackend.exceptions.account.throwable.AccountDoesNotExistException;
import pl.edu.pw.ee.bankbackend.exceptions.account.throwable.NotEnoughMoneyException;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public final TransactionResponse makeNewTransaction(TransactionRequest request, User from) {
        log.info("Making new transaction for user: {}", from);

        BankAccount toAccount = accountRepository.findByBillNumber(request.to())
                .orElseThrow(() -> new AccountDoesNotExistException("Account not found"));
        BankAccount fromAccount = accountRepository.findByUser(from)
                .orElseThrow(() -> new AccountDoesNotExistException("Account not found"));
        User to = toAccount.getUser();

        log.info("Found accounts: {} and {}", toAccount, fromAccount);

        if (fromAccount.getBalance() < request.amount()) {
            log.error("Not enough money on account to make transaction!");

            throw new NotEnoughMoneyException("Not enough money on account to make transaction!");
        }
        Transaction transaction = buildTransaction(request, to, from);
        Transaction savedTransaction = transactionRepository.save(transaction);

        toAccount.addAccountBalance(request.amount());
        fromAccount.subtractAccountBalance(request.amount());

        accountRepository.save(toAccount);
        accountRepository.save(fromAccount);

        log.info("Transaction saved: {}", savedTransaction);

        return transactionMapper.mapToTransactionResponse(savedTransaction);
    }

    @Override
    public final List<TransactionResponse> getTransactions(User user) {
        log.info("Getting transactions for user: {}", user);

        List<Transaction> transactions = transactionRepository.findAllByUser(user);

        log.info("Found '{}' transactions", transactions.size());

        return transactions
                .stream()
                .map(transactionMapper::mapToTransactionResponse)
                .toList();
    }

    private Transaction buildTransaction(TransactionRequest request, User to, User from) {
        return Transaction
                .builder()
                .amount(request.amount())
                .date(LocalDate.now())
                .title(request.title())
                .to(to)
                .from(from)
                .build();
    }
}
