package pl.edu.pw.ee.bankbackend.api.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.bankbackend.api.account.data.AccountResponse;
import pl.edu.pw.ee.bankbackend.api.account.interfaces.AccountController;
import pl.edu.pw.ee.bankbackend.api.account.interfaces.AccountService;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import static pl.edu.pw.ee.bankbackend.api.account.constants.AccountMappings.ACCOUNT_ENDPOINT_MAPPING;
import static pl.edu.pw.ee.bankbackend.api.account.constants.AccountMappings.GET_ACCOUNT_DETAILS_MAPPING;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ACCOUNT_ENDPOINT_MAPPING)
public class AccountControllerImpl implements AccountController {
    private final AccountService accountService;

    @Override
    @GetMapping(value = GET_ACCOUNT_DETAILS_MAPPING)
    public final AccountResponse getAccountDetails(@AuthenticationPrincipal User user) {
        return accountService.getAccountDetails(user);
    }
}
