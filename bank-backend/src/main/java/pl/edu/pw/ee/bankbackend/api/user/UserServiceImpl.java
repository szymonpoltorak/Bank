package pl.edu.pw.ee.bankbackend.api.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.bankbackend.api.user.data.UserResponse;
import pl.edu.pw.ee.bankbackend.api.user.interfaces.UserService;
import pl.edu.pw.ee.bankbackend.entities.account.BankAccount;
import pl.edu.pw.ee.bankbackend.entities.account.interfaces.AccountRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;
import pl.edu.pw.ee.bankbackend.exceptions.account.throwable.AccountDoesNotExistException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AccountRepository accountRepository;

    @Override
    public final UserResponse getUserAccountInfo(User user) {
        BankAccount bankAccount = accountRepository.findByUser(user)
                .orElseThrow(() -> new AccountDoesNotExistException("Account not found"));

        log.info("User account info for user: {} has been successfully retrieved", user.getUsername());

        return UserResponse
                .builder()
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .billNumber(bankAccount.getBillNumber())
                .idCardNumber(user.getIdCardNumber())
                .build();
    }
}
