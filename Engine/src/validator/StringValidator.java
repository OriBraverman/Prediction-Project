package validator;

public class StringValidator {
    public static boolean validateStringIsInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
