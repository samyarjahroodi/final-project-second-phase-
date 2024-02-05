package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.Card;
import finalproject.finalproject.repository.CardRepository;
import finalproject.finalproject.service.CardService;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
public class CardServiceImpl
        implements CardService {
    private final CardRepository cardRepository;

    @Override
    public <S extends Card> S save(S entity) {
        return cardRepository.save(entity);
    }

    @Override
    public Optional<Card> findById(Integer integer) {
        return cardRepository.findById(integer);
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public List<Card> findAllById(Iterable<Integer> integers) {
        return cardRepository.findAllById(integers);
    }

    @Override
    public List<Card> findAll(Sort sort) {
        return cardRepository.findAll(sort);
    }

    @Override
    public Page<Card> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer integer) {
        cardRepository.deleteById(integer);
    }

    @Override
    public void delete(Card entity) {
        cardRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends Card> entities) {
        cardRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        cardRepository.deleteAll();
    }

    @Override
    public Card getReferenceById(Integer integer) {
        return cardRepository.getOne(integer);
    }
}
