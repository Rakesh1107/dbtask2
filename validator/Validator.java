package validator;

import java.util.regex.Pattern;

public class Validator {
    // Validate if all fields are filled
    public static boolean validate(String... fields) {
        for (String field : fields) {
            if (field == null || field.length() == 0) {
                return false;
            }
        }
        return true;
    }

    // Validate if mobile number is 10 digits long and starts with either 6/7/8/9
    public static boolean validateMobileNumber(long mobileNumber) {
        String mobile = String.valueOf(mobileNumber);

        if (mobile.length() == 10) {
            char firstNumber = mobile.charAt(0);
            return firstNumber == '6' || firstNumber == '7' || firstNumber == '8' || firstNumber == '9';
        }

        return false;
    }

    // Validate if name does not contain special characters
    public static boolean validateName(String name) {
          return Pattern.matches("[A-Za-z]+", name);
    }

    // Validate if amount is a multiple of 100 (minimum denomination)
    public static boolean validateMoney(long amount) {
        return amount % 100 != 0;
    }
}
