package finalproject.finalproject.repository.impl;

import base.reposiotry.Impl.BaseEntityRepositoryImpl;
import entity.utility.Wallet;
import repository.WalletRepository;

import javax.persistence.EntityManager;

public class WalletRepositoryImpl
        extends BaseEntityRepositoryImpl<Wallet, Integer>
        implements WalletRepository {


    public WalletRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Wallet> getEntityClass() {
        return Wallet.class;
    }
}
