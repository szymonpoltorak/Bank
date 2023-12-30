package pl.edu.pw.ee.bankbackend.api.transaction;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.bankbackend.api.transaction.data.TransactionRequest;
import pl.edu.pw.ee.bankbackend.api.transaction.data.TransactionResponse;
import pl.edu.pw.ee.bankbackend.api.transaction.interfaces.TransactionController;
import pl.edu.pw.ee.bankbackend.api.transaction.interfaces.TransactionService;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.util.List;

import static pl.edu.pw.ee.bankbackend.api.transaction.constants.TransactionMappings.GET_TRANSACTIONS;
import static pl.edu.pw.ee.bankbackend.api.transaction.constants.TransactionMappings.MAKE_TRANSACTION;
import static pl.edu.pw.ee.bankbackend.api.transaction.constants.TransactionMappings.TRANSACTION_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = TRANSACTION_ENDPOINT)
public class TransactionControllerImpl implements TransactionController {
    private final TransactionService transactionService;

    @Override
    @PostMapping(value = MAKE_TRANSACTION)
    public final TransactionResponse makeNewTransaction(@Valid @RequestBody TransactionRequest request,
                                                        @AuthenticationPrincipal User from) {
        return transactionService.makeNewTransaction(request, from);
    }

    @Override
    @GetMapping(value = GET_TRANSACTIONS)
    public final List<TransactionResponse> getTransactions(@AuthenticationPrincipal User user) {
        return transactionService.getTransactions(user);
    }
}
