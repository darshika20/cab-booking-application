package application.utility;

public class Validation {

    public static boolean notNull(Object object){
        return object != null;
    }

    public static boolean notEmptyString(String string) {
        return  string != null && !((string.trim()).isEmpty()) && !string.isEmpty();
    }
}
