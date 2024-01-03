package pl.edu.pw.ee.bankbackend.api.auth.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.bankbackend.api.auth.utils.interfaces.PasswordCombinationService;
import pl.edu.pw.ee.bankbackend.entities.password.PasswordCombination;
import pl.edu.pw.ee.bankbackend.entities.password.interfaces.PasswordCombinationRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.random.RandomGenerator;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordCombinationServiceImpl implements PasswordCombinationService {
    private static final int NUMBER_OF_COMBINATIONS = 10;
    private static final int INITIAL_HASH_SIZE = 40;
    private static final int COMBINATION_LENGTH = 6;
    private static final int SEED = 32;

    private final PasswordCombinationRepository passwordCombinationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public final void generateCombinationsForPassword(String password, User user) {
        Collection<String> codedCombinations = new HashSet<>(INITIAL_HASH_SIZE);

        log.info("Generating combinations for passwordCombination");

        while (codedCombinations.size() < NUMBER_OF_COMBINATIONS) {
            Set<Integer> combination = generateCombination(password);

            String codedCombination = codeCombination(combination);

            codedCombinations.add(codedCombination);
        }
        codedCombinations
                .parallelStream()
                .forEach(codedCombination -> saveNewPasswordOption(codedCombination, password, user));
    }

    @Override
    public final String getPasswordCombinationForUser(String username) {
        List<PasswordCombination> passwordCombinations = passwordCombinationRepository.findAllByUserUsername(username);

        log.info("Got '{}' password combinations for user: {}", passwordCombinations.size(), username);

        if (passwordCombinations.isEmpty()) {
            throw new IllegalArgumentException("User has no password combinations");
        }
        int index = new SecureRandom(SecureRandom.getSeed(SEED))
                .nextInt(passwordCombinations.size() - 1);

        log.info("Index of password combination: {}", index);

        String passwordCombination = passwordCombinations.get(index).getCodedCombination();

        log.info("Password combination: {}", passwordCombination);

        return passwordCombination;
    }

    private void saveNewPasswordOption(String codedCombination, String password, User user) {
        String combinationToHash = Arrays
                .stream(codedCombination.split(";"))
                .map(character -> password.charAt(Integer.parseInt(character)))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        log.info("Coded combination: {}", codedCombination);
        log.info("Combination to hash: {}", combinationToHash);

        PasswordCombination passwordCombination = PasswordCombination
                .builder()
                .combinationHash(passwordEncoder.encode(combinationToHash))
                .codedCombination(codedCombination)
                .user(user)
                .build();
        passwordCombinationRepository.save(passwordCombination);
    }

    private String codeCombination(Collection<Integer> combination) {
        return combination
                .stream()
                .collect(StringBuilder::new, (builder, i) -> builder.append(i).append(";"), StringBuilder::append)
                .toString();
    }

    private Set<Integer> generateCombination(CharSequence password) {
        RandomGenerator random = new SecureRandom(SecureRandom.getSeed(SEED));
        Set<Integer> combinations = new TreeSet<>();

        while (combinations.size() != COMBINATION_LENGTH) {
            int index = random.nextInt(password.length() - 1);

            combinations.add(index);
        }
        return combinations;
    }
}
