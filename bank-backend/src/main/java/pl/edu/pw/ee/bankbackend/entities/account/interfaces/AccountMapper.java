package pl.edu.pw.ee.bankbackend.entities.account.interfaces;

import org.mapstruct.Mapper;
import pl.edu.pw.ee.bankbackend.api.account.data.AccountResponse;
import pl.edu.pw.ee.bankbackend.entities.account.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponse mapToAccountResponse(Account account);
}
