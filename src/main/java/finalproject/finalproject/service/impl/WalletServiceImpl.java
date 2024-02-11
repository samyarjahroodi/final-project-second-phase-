package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.payment.Wallet;
import finalproject.finalproject.exception.NullInputException;
import finalproject.finalproject.repository.WalletRepository;
import finalproject.finalproject.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl
        implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public <S extends Wallet> S save(S entity) {
        return walletRepository.save(entity);
    }

    @Override
    public Optional<Wallet> findById(Integer integer) {
        return walletRepository.findById(integer);
    }

    @Override
    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @Override
    public List<Wallet> findAllById(Iterable<Integer> integers) {
        return walletRepository.findAllById(integers);
    }

    @Override
    public List<Wallet> findAll(Sort sort) {
        return walletRepository.findAll(sort);
    }

    @Override
    public Page<Wallet> findAll(Pageable pageable) {
        return walletRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer integer) {
        walletRepository.deleteById(integer);
    }

    @Override
    public void delete(Wallet entity) {
        walletRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends Wallet> entities) {
        walletRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        walletRepository.deleteAll();
    }

    @Override
    public Wallet getReferenceById(Integer integer) {
        return walletRepository.findById(integer).orElseThrow(() -> new NullInputException("null"));
    }
}
