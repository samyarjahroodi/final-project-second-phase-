package finalproject.finalproject.utility;

import repository.impl.*;
import service.impl.*;

import javax.persistence.EntityManager;

@SuppressWarnings("unused")
public class ApplicationContext {

    private static final EntityManager ENTITY_MANAGER;
    private static final CustomerOrderRepositoryImpl CUSTOMER_ORDER_REPOSITORY;
    private static final CustomerRepositoryImpl CUSTOMER_REPOSITORY;
    private static final DutyRepositoryImpl DUTY_REPOSITORY;
    private static final ExpertRepositoryImpl EXPERT_REPOSITORY;
    private static final SubDutyRepositoryImpl SUB_DUTY_REPOSITORY;
    private static final SuggestionRepositoryImpl SUGGESTION_REPOSITORY;
    private static final WalletRepositoryImpl WALLET_REPOSITORY;


    private static final CustomerOrderServiceImpl CUSTOMER_ORDER_SERVICE;
    private static final CustomerServiceImpl CUSTOMER_SERVICE;
    private static final DutyServiceImpl DUTY_SERVICE;
    private static final ExpertServiceImpl EXPERT_SERVICE;
    private static final SubDutyServiceImpl SUB_DUTY_SERVICE;
    private static final SuggestionServiceImpl SUGGESTION_SERVICE;
    private static final WalletServiceImpl WALLET_SERVICE;


    static {
        ENTITY_MANAGER = EntityManagerProvider.getEntityManager();
        CUSTOMER_ORDER_REPOSITORY = new CustomerOrderRepositoryImpl(ENTITY_MANAGER);
        CUSTOMER_REPOSITORY = new CustomerRepositoryImpl(ENTITY_MANAGER);
        DUTY_REPOSITORY = new DutyRepositoryImpl(ENTITY_MANAGER);
        EXPERT_REPOSITORY = new ExpertRepositoryImpl(ENTITY_MANAGER);
        SUB_DUTY_REPOSITORY = new SubDutyRepositoryImpl(ENTITY_MANAGER);
        SUGGESTION_REPOSITORY = new SuggestionRepositoryImpl(ENTITY_MANAGER);
        WALLET_REPOSITORY = new WalletRepositoryImpl(ENTITY_MANAGER);

        CUSTOMER_ORDER_SERVICE = new CustomerOrderServiceImpl(CUSTOMER_ORDER_REPOSITORY);
        CUSTOMER_SERVICE = new CustomerServiceImpl(CUSTOMER_REPOSITORY);
        DUTY_SERVICE = new DutyServiceImpl(DUTY_REPOSITORY);
        EXPERT_SERVICE = new ExpertServiceImpl(EXPERT_REPOSITORY);
        SUB_DUTY_SERVICE = new SubDutyServiceImpl(SUB_DUTY_REPOSITORY);
        SUGGESTION_SERVICE = new SuggestionServiceImpl(SUGGESTION_REPOSITORY);
        WALLET_SERVICE = new WalletServiceImpl(WALLET_REPOSITORY);
    }


    public static CustomerServiceImpl getCustomerService() {
        return CUSTOMER_SERVICE;
    }

    public static CustomerOrderServiceImpl getCustomerOrderService() {
        return CUSTOMER_ORDER_SERVICE;
    }

    public static DutyServiceImpl getDutyService() {
        return DUTY_SERVICE;
    }

    public static ExpertServiceImpl getExpertService() {
        return EXPERT_SERVICE;
    }

    public static SubDutyServiceImpl getSubDutyService() {
        return SUB_DUTY_SERVICE;
    }

    public static SuggestionServiceImpl getSuggestionService() {
        return SUGGESTION_SERVICE;
    }

    public static WalletServiceImpl getWalletService() {
        return WALLET_SERVICE;
    }
}