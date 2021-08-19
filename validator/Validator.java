package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean validate(String... fields) {
        for (String field : fields) {
            if (field == null || field.length() == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateMobileNumber(long mobileNumber) {
        String mobile = String.valueOf(mobileNumber);

        if (mobile.length() == 10) {
            char firstNumber = mobile.charAt(0);
            return firstNumber == '6' || firstNumber == '7' || firstNumber == '8' || firstNumber == '9';
        } else {
            return false;
        }
    }

    public static boolean validateName(String name) {
          Pattern pattern = Pattern.compile("[A-Za-z]+");
          Matcher matcher = pattern.matcher(name);
          return matcher.matches();
    }
}
