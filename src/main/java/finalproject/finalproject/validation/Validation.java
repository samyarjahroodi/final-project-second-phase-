//package finalproject.finalproject.validation;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@SuppressWarnings("unused")
//public class Validation {
//    public static boolean isValidPassword(String password) {
//        String regex = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(password);
//        return matcher.matches();
//    }
//
//
//    public static boolean isValidDate(String date) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setLenient(false);
//
//        try {
//            Date parsedDate = dateFormat.parse(date);
//            return true;
//        } catch (ParseException e) {
//            return false;
//        }
//    }
//
//    public static boolean isValidEmail(String email) {
//        final Pattern EMAIL_PATTERN =
//                Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
//        return EMAIL_PATTERN.matcher(email).matches();
//    }
//
//    public static String setPassword(String password) {
//        boolean validPassword = Validation.isValidPassword(password);
//        if (validPassword) {
//            return password;
//        } else {
//            System.out.println("not strong password!!!");
//        }
//        return null;
//    }
//
//    public static String setEmail(String email) {
//        boolean validPassword = Validation.isValidEmail(email);
//        if (validPassword) {
//            return email;
//        } else {
//            System.out.println("false email address!!!");
//        }
//        return null;
//    }
//}
