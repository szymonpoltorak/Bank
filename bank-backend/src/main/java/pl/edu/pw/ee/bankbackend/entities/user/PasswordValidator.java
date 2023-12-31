package pl.edu.pw.ee.bankbackend.entities.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import pl.edu.pw.ee.bankbackend.entities.user.interfaces.Password;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class PasswordValidator implements ConstraintValidator<Password, String> {
    private static final Pattern PASSWORD_PATTERN = Pattern
            .compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[:\\?\\.@!#:\\-_=+ %&])[a-zA-Z0-9:\\?\\.@!#:\\-_=+ %&]+$");

    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile(".*[a-z].*");

    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile(".*[A-Z].*");

    private static final Pattern ONE_DIGIT_PATTERN = Pattern.compile(".*\\d.*");

    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile(".*[:\\?\\.@!#:\\-_=+ %&].*");

    private static final int MIN_LENGTH = 10;

    private static final int MAX_LENGTH = 30;
    private static final double MIN_ENTROPY = 3.0;

    @Override
    public final boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        Matcher matcher = PASSWORD_PATTERN.matcher(value);

        if (matcher.matches()) {
            return true;
        }
        int passwordLength = value.length();

        context.disableDefaultConstraintViolation();

        if (!LOWER_CASE_PATTERN.matcher(value).matches()) {
            context
                    .buildConstraintViolationWithTemplate("Password must contain at least one lowercase letter")
                    .addConstraintViolation();
        }
        if (!UPPER_CASE_PATTERN.matcher(value).matches()) {
            context
                    .buildConstraintViolationWithTemplate("Password must contain at least one uppercase letter")
                    .addConstraintViolation();
        }
        if (!ONE_DIGIT_PATTERN.matcher(value).matches()) {
            context
                    .buildConstraintViolationWithTemplate("Password must contain at least one digit")
                    .addConstraintViolation();
        }
        if (!SPECIAL_CHARACTER_PATTERN.matcher(value).matches()) {
            context
                    .buildConstraintViolationWithTemplate("Password must contain at least one special character [:?.@!#:-_=+ ]")
                    .addConstraintViolation();
        }
        if (passwordLength < MIN_LENGTH || passwordLength > MAX_LENGTH) {
            context
                    .buildConstraintViolationWithTemplate("Password must be between 8 and 20 characters long")
                    .addConstraintViolation();
        }
        if (calculatePasswordEntropy(value) < MIN_ENTROPY) {
            context
                    .buildConstraintViolationWithTemplate("Password must have at least 3.0 of entropy")
                    .addConstraintViolation();
        }
        return false;
    }

    private double calculatePasswordEntropy(String input) {
        int length = input.length();
        double entropy = 0.0;
        final int numOfChars = 256;
        int[] charCount = new int[numOfChars];

        for (char c : input.toCharArray()) {
            charCount[c]++;
        }

        for (int count : charCount) {
            if (count > 0) {
                double probability = (double) count / length;

                entropy -= probability * (Math.log(probability) / Math.log(2.0));
            }
        }
        return entropy;
    }
}
