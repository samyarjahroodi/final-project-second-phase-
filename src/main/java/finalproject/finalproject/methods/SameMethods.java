//package finalproject.finalproject.methods;
//
//import entity.duty.Duty;
//import entity.duty.SubDuty;
//import service.impl.DutyServiceImpl;
//import service.impl.SubDutyServiceImpl;
//import utility.ApplicationContext;
//import validation.Validation;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//
// public class SameMethods {
//    static DutyServiceImpl dutyService = ApplicationContext.getDutyService();
//    static SubDutyServiceImpl subDutyService = ApplicationContext.getSubDutyService();
//
//    protected static String setPassword(String password) {
//        boolean validPassword = Validation.isValidPassword(password);
//        if (validPassword) {
//            return password;
//        } else {
//            System.out.println("not strong password!!!");
//        }
//        return null;
//    }
//
//    protected static String setEmail(String email) {
//        boolean validPassword = Validation.isValidEmail(email);
//        if (validPassword) {
//            return email;
//        } else {
//            System.out.println("false email address!!!");
//        }
//        return null;
//    }
//
//    protected static byte[] setImageForExpert() throws IOException {
//        while (true) {
//            byte[] imageData =
//                    Files.readAllBytes(Paths.get("C:\\Users\\Samyar\\Desktop\\images.jpg"));
//            if (imageData.length > 300 * 1024) {
//                System.out.println("size is not supported!");
//            } else {
//                return imageData;
//            }
//        }
//    }
//
////    protected static Integer giveStarToNewExpert() {
////        Expert expert = new Expert();
////        if (expert.getRegistrationStatus().equals(AWAITING_CONFIRMATION)) {
////            expert.setStar(0);
////            return 0;
////        } else if (expert.getSuggestions().isEmpty() || expert.getSuggestions().stream().noneMatch(Suggestion::getIsApproved)) {
////            expert.setStar(0);
////            return 0;
////        }
////        return null;
////    }
//
//    public static List<Duty> showDuties() {
//        List<Duty> result = new ArrayList<>();
//        for (Duty d : dutyService.findAll()) {
//            result.add(d);
//            System.out.println(d);
//        }
//        return result;
//    }
//
//    public static List<SubDuty> showSubDuties() {
//        List<SubDuty> result = new ArrayList<>();
//        for (SubDuty s : subDutyService.findAll()) {
//            result.add(s);
//            System.out.println(s);
//        }
//        return result;
//    }
//}
