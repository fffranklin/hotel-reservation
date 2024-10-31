package util;

import java.util.regex.Pattern;

public class Validator {

    public static Integer validateIfInputIsANumber(String input) {
        int inputNumber = 0;

        try {
            inputNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return inputNumber;
        }

        return inputNumber;
    }

    public static double validateIfInputIsDouble(String input) {
        double inputDouble = 0.0;

        try {
            inputDouble = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return inputDouble;
        }

        return inputDouble;
    }

    public static boolean isValidEmail(String email) {
        String emailRegEx = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegEx);

        return pattern.matcher(email).find();
    }
}
