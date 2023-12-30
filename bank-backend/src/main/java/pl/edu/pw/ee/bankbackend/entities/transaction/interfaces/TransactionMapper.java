package pl.edu.pw.ee.bankbackend.entities.transaction.interfaces;

import pl.edu.pw.ee.bankbackend.api.transaction.data.TransactionResponse;
import pl.edu.pw.ee.bankbackend.entities.transaction.Transaction;

@FunctionalInterface
public interface TransactionMapper {
    TransactionResponse mapToTransactionResponse(Transaction transaction);
}
