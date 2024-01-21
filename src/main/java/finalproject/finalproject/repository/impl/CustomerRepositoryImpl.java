package finalproject.finalproject.repository.impl;

import base.reposiotry.Impl.BaseEntityRepositoryImpl;
import entity.user.Customer;
import repository.CustomerRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class CustomerRepositoryImpl
        extends BaseEntityRepositoryImpl<Customer, Integer>
        implements CustomerRepository {


    public CustomerRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Customer> getEntityClass() {
        return Customer.class;
    }

    @Override
    public Customer getId(String username, String password) {
        try {
            Customer customer = entityManager.createQuery("SELECT c FROM Customer c" +
                            " WHERE username = :username AND password = :password",Customer.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
            return (Customer) customer;
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}
