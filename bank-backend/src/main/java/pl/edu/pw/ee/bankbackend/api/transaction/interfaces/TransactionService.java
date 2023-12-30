package pl.edu.pw.ee.bankbackend.api.transaction.interfaces;

import pl.edu.pw.ee.bankbackend.api.transaction.data.TransactionRequest;
import pl.edu.pw.ee.bankbackend.api.transaction.data.TransactionResponse;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.util.List;

public interface TransactionService {
    TransactionResponse makeNewTransaction(TransactionRequest request, User from);

    List<TransactionResponse> getTransactions(User user);
}
