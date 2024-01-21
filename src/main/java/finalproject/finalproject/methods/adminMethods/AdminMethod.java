package finalproject.finalproject.methods.adminMethods;


import finalproject.finalproject.methods.SameMethods;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


public class AdminMethod extends SameMethods {
    static DutyServiceImpl dutyService = ApplicationContext.getDutyService();
    static SubDutyServiceImpl subDutyService = ApplicationContext.getSubDutyService();
    static ExpertServiceImpl expertService = ApplicationContext.getExpertService();
    static WalletServiceImpl walletService = ApplicationContext.getWalletService();
    static Duty duty = new Duty();
    static Wallet wallet = new Wallet();
    static SubDuty subDuty = new SubDuty();
    static Expert expert = new Expert();
    static Scanner scanner = new Scanner(System.in);

    public static void createDuty(String nameOfDuty) {
        duty.setName(nameOfDuty);
        SecurityContext.fillContext(duty);
        dutyService.saveOrUpdate(duty);
    }


    public static void deleteSubDutyFromTheExistDuty() {
        showSubDuties();
        System.out.println("id : ");
        int i = scanner.nextInt();
        subDutyService.delete(i);

    }

    public static void deleteDuty() {
        showDuties();
        System.out.println("id : ");
        int i = scanner.nextInt();
        dutyService.delete(i);

    }

    public static void deleteExpert() {
        showExpert();
        System.out.println("id : ");
        int i = scanner.nextInt();
        expertService.delete(i);
    }

    public static List<Expert> showExpert() {
        List<Expert> experts = new ArrayList<>();
        for (Expert e : expertService.findAll()) {
            experts.add(e);
            System.out.println(e);
        }
        return experts;
    }

    public static void createExpert() throws IOException {
        expert.setFirstname("ali");
        expert.setLastname("jaodi");
        expert.setEmail(setEmail("aliddd@gmail.com"));
        expert.setPassword(setPassword("12345Avbdsfsfs"));
        expert.setUsername("ali");
        Wallet wallet = new Wallet();
        wallet.setCreditOfWallet(0);
        walletService.saveOrUpdate(wallet);
        expert.setWallet(wallet);
        expert.setRegistrationStatus(AWAITING_CONFIRMATION);
        expert.setStar(expertService.averageStarOfExpert(expert));
        expert.setWhenExpertRegistered(LocalDate.now());
        expert.setImage(setImageForExpert());
        //SecurityContext.fillContext(expert);
        expertService.saveOrUpdate(expert);
    }

    //update the description and price of the sub duty!!
    public static void updateDetailsForSubDuty(String description, int price) {
        try {
            showSubDuties();
            System.out.println("which sub duty do you want to update");
            int i = scanner.nextInt();
            Optional<SubDuty> subDuty = subDutyService.findById(i);
            if (subDuty.isPresent()) {
                SubDuty subDuty1 = subDuty.get();
                subDuty1.setPrice(price);
                subDuty1.setDescription(description);
                subDutyService.saveOrUpdate(subDuty1);
            } else {
                System.out.println("not found!!!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    public static void changeTheStatusOfExpert(Integer id) {
        expertService.changeRegistrationStatus(id);
        addSubDutyToNewExpert(id);

    }


    public static void createSubDuty(String nameOfSubDuty, String description) {
        try {
            System.out.println("what is the id of the duty you want to add sub duty to?");
            showDuties();
            int i = scanner.nextInt();
            if (dutyService.findById(i).isPresent()) {
                Optional<Duty> dutyById = dutyService.findById(i);
                Duty duty = dutyById.get();
                Collection<SubDuty> allSubDuties = subDutyService.findAll();
                for (SubDuty s : allSubDuties) {
                    if (s.getName().equals(nameOfSubDuty)) {
                        System.out.println("your sub duty is already exists");
                        System.exit(0);
                    }
                }
                subDuty.setName(nameOfSubDuty);
                subDuty.setDescription(description);
                subDuty.setPrice(2000);
                subDuty.setDuty(duty);
                SecurityContext.fillContext(subDuty);
                subDutyService.saveOrUpdate(subDuty);

            } else {
                System.out.println("did not find in database your duty");
            }
        } catch (
                InputMismatchException e) {
            e.printStackTrace();
        }
    }

    public static void addSubDutyToNewExpert(Integer id) {
        try {
            System.out.println("which subDuty:");
            AdminMethod.showSubDuties();
            int i = scanner.nextInt();
            Optional<SubDuty> subDutyById = subDutyService.findById(i);
            if (subDutyById.isPresent()) {
                SubDuty subDuty = subDutyById.get();
                Optional<Expert> expertById = expertService.findById(id);
                Expert expert = expertById.get();
                subDuty.setExperts(Collections.singletonList(expert));
                expertService.saveOrUpdate(expert);
            } else {
                throw new NullPointerException("not found!!!");
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
    }
}