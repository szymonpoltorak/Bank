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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordCombinationServiceImpl implements PasswordCombinationService {
    private static final int NUMBER_OF_COMBINATIONS = 10;
    private static final int INITIAL_HASH_SIZE = 40;
    private static final long COMBINATION_LENGTH = 6L;

    private final PasswordOptionRepository passwordOptionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public final void generateCombinationsForPassword(String password, User user) {
        Set<String> codedCombinations = new HashSet<>(INITIAL_HASH_SIZE);

        log.info("Generating combinations for passwordCombination");

        while (codedCombinations.size() < NUMBER_OF_COMBINATIONS) {
            Set<Integer> combination = generateCombination(password);

            log.info("Generated combination: {}", combination);

            String codedCombination = codeCombination(combination);

            log.info("Coded combination: {}", codedCombination);

            codedCombinations.add(codedCombination);
        }
        codedCombinations
                .parallelStream()
                .forEach(codedCombination -> saveNewPasswordOption(codedCombination, password, user));
    }

    private void saveNewPasswordOption(String codedCombination, String password, User user) {
        String combinationToHash = Arrays
                .stream(codedCombination.split(";"))
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

    private String codeCombination(Set<Integer> combination) {
        return combination
                .stream()
                .collect(StringBuilder::new, (builder, i) -> builder.append(i).append(";"), StringBuilder::append)
                .toString();
    }

    private Set<Integer> generateCombination(String password) {
        SecureRandom random = new SecureRandom();
        Set<Integer> combinations = new TreeSet<>();

        while(combinations.size() != COMBINATION_LENGTH) {
            int index = random.nextInt(0, password.length() - 1);

            combinations.add(index);
        }
        return combinations;
    }
}
