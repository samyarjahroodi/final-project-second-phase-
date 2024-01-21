package finalproject.finalproject.service.impl;


import base.service.Impl.BaseEntityServiceImpl;
import entity.utility.Wallet;
import repository.WalletRepository;
import service.WalletService;

public class WalletServiceImpl
        extends BaseEntityServiceImpl<Wallet, Integer, WalletRepository>
        implements WalletService {

    public WalletServiceImpl(WalletRepository repository) {
        super(repository);
    }
}
