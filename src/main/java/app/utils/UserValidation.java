package app.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import app.entities.user.User;
import app.entities.user.UserService;
import app.entities.user.dt.UserLogIn;
import lombok.Getter;

public class UserValidation {

    UserService userService;
    @Getter
    InnerUserValidation validationResponse = new InnerUserValidation(new ArrayList<>(), new ArrayList<>(),
            new ArrayList<>(), false, Optional.empty());

    public UserValidation(UserService userService) {
        this.userService = userService;
    }

    public void validate(UserLogIn user) {
        List<String> userNameErrors = new ArrayList<>();
        List<String> passwordErrors = new ArrayList<>();

        Optional<User> response = userLogIn(user);

        if (!response.isPresent()) {
            userNameErrors.add("Numele de utilizator si/sau parola sunt gresite;");

            this.validationResponse = new InnerUserValidation(userNameErrors, passwordErrors, new ArrayList<>(), false,
                    response);
            return;
        }

        if (!isUserNameAllowedChars(user.userName()))
            userNameErrors.add("Caractere acceptate: [A-Za-z0-9];");

        // if (!isUserNameLengthValid(user.userName()))
        // userNameErrors.add("Lungime minima: 8 caractere;");

        // if (!isPasswordLengthValid(user.password()))
        // passwordErrors.add("Lungime minima: 8 caractere;");

        // if (!hasPasswordUpperCase(user.password()))
        // passwordErrors.add("Nu are o litera mare;");

        // if (!hasPasswordLowerCase(user.password()))
        //     passwordErrors.add("Nu are o litera mica;");

        // if (!hasPasswordNumber(user.password()))
        //     passwordErrors.add("Nu are numere;");

        if (isPasswordaAllowedChars(user.password()))
            passwordErrors.add("Nu este voie cu urmatoarele caractere:\n \\, \", ;, =, <, >, (, ), [, ], {, }, `");

        if (userNameErrors.size() > 0 || passwordErrors.size() > 0)
            this.validationResponse = new InnerUserValidation(userNameErrors, passwordErrors, new ArrayList<>(), false,
                    response);
        this.validationResponse = new InnerUserValidation(userNameErrors, passwordErrors, new ArrayList<>(), true,
                response);
    }

    private Optional<User> userLogIn(UserLogIn user) {
        return userService.LogInUser(user);
    }

    private boolean isUserNameAllowedChars(String userName) {
        String regex = "[^A-Za-z0-9]";
        return !userName.matches(regex);
    }

    private boolean isUserNameLengthValid(String userName) {
        int length = userName.length();
        return length >= 8 && length <= 50;
    }

    private boolean isPasswordLengthValid(String password) {
        return password.length() >= 8 && password.length() < 50;
    }

    private boolean hasPasswordUpperCase(String password) {
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPasswordLowerCase(String password) {
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPasswordNumber(String password) {
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCnpOnlyDigits(String cnp) {
        for (int i = 0; i < cnp.length(); i++) {
            char c = cnp.charAt(i);
            if (!Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCnpLengthValid(String cnp) {
        return cnp.length() == 13;
    }

    private boolean isPasswordaAllowedChars(String password) {

        List<Character> characters = Arrays.asList('\'', '"', ';', '=', '<', '>', '(', ')', '[', ']', '{', '}', '`');

        return password.chars().mapToObj(c -> (char) c).anyMatch(characters::contains);
    }

    public record InnerUserValidation(List<String> userNameErrors, List<String> passwordErrors, List<String> cnpErrors,
            boolean isValid, Optional<User> getUser) {
    }
}
