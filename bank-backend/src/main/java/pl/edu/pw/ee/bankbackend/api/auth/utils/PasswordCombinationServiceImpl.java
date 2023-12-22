package pl.edu.pw.ee.bankbackend.api.auth.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.bankbackend.api.auth.utils.interfaces.PasswordCombinationService;
import pl.edu.pw.ee.bankbackend.entities.password.PasswordOption;
import pl.edu.pw.ee.bankbackend.entities.password.interfaces.PasswordOptionRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordCombinationServiceImpl implements PasswordCombinationService {
    private static final int NUMBER_OF_COMBINATIONS = 10;
    private static final long COMBINATION_LENGTH = 6L;

    private final PasswordOptionRepository passwordOptionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public final void generateCombinationsForPassword(String password, User user) {
        Collection<String> codedCombinations = new ArrayList<>(NUMBER_OF_COMBINATIONS);
        int i = 0;

        log.info("Generating combinations for passwordCombination");

        while (i++ < NUMBER_OF_COMBINATIONS) {
            List<Integer> combination = generateCombination(password);

            log.info("Generated combination: {}", combination);

            String codedCombination = codeCombination(combination);

            log.info("Coded combination: {}", codedCombination);

            if (codedCombinations.contains(codedCombination)) {
                log.info("Combination already exists, generating another one");
                i--;

                continue;
            }
            codedCombinations.add(codedCombination);

            saveNewPasswordOption(codedCombination, password, user);
        }
    }

    private void saveNewPasswordOption(String codedCombination, String password, User user) {
        String combinationToHash = Arrays.stream(codedCombination.split(";"))
                .map(character -> password.charAt(Integer.parseInt(character)))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        log.info("Combination to hash: {}", combinationToHash);

        PasswordOption passwordOption = PasswordOption
                .builder()
                .combinationHash(passwordEncoder.encode(combinationToHash))
                .codedCombination(codedCombination)
                .user(user)
                .build();
        passwordOptionRepository.save(passwordOption);
    }

    private String codeCombination(List<Integer> combination) {
        return combination
                .stream()
                .collect(StringBuilder::new, (builder, i) -> builder.append(i).append(";"), StringBuilder::append)
                .toString();
    }

    private List<Integer> generateCombination(String password) {
        // TODO: 2021-06-09 check if combination is unique
        return new SecureRandom()
                .ints(COMBINATION_LENGTH, 0, password.length())
                .boxed()
                .sorted()
                .toList();
    }
}
