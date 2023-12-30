package pl.edu.pw.ee.bankbackend.entities.transaction;

import org.springframework.stereotype.Component;
import pl.edu.pw.ee.bankbackend.api.transaction.data.TransactionResponse;
import pl.edu.pw.ee.bankbackend.entities.transaction.interfaces.TransactionMapper;

@Component
public class TransactionMapperImpl implements TransactionMapper {
    @Override
    public final TransactionResponse mapToTransactionResponse(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        return TransactionResponse
                .builder()
                .title(transaction.getTitle())
                .to(transaction.getTo().getFullName())
                .from(transaction.getFrom().getFullName())
                .amount(transaction.getAmount())
                .date(transaction.getDate().toString())
                .build();
    }
}
