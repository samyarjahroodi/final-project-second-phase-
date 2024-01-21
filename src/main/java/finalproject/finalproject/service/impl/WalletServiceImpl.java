package finalproject.finalproject.service.impl;

import finalproject.finalproject.repository.WalletRepository;
import finalproject.finalproject.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl
        implements WalletService {
    private final WalletRepository walletRepository;

}
