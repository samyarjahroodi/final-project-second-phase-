package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.payment.Card;
import finalproject.finalproject.exception.DuplicateException;
import finalproject.finalproject.exception.NotValidSizeException;
import finalproject.finalproject.exception.NullInputException;
import finalproject.finalproject.repository.CardRepository;
import finalproject.finalproject.service.CardService;

import finalproject.finalproject.service.dto.request.CardDtoRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
public class CardServiceImpl
        implements CardService {
    private final CardRepository cardRepository;
    private final CustomerServiceImpl customerService;

    public Card creatCard(CardDtoRequest dto) {
        if (dto.getCardNumber() == null){
            throw new NullInputException("card number cannot be null");
        }
            Card card = new Card();
        card.setCardNumber(dto.getCardNumber());
        for (Card c : cardRepository.findAll()) {
            if (c.getCardNumber().equals(card.getCardNumber())) {
                throw new DuplicateException("card number already exists in database");
            }
        }
        if (card.getCardNumber().length() != 16) {
            throw new NotValidSizeException("card number should be 16");
        }

        card.setCvv2(dto.getCvv2());
        card.setPassword(dto.getPassword());
        int year = dto.getYear() + 1400;
        LocalDate expireDate = LocalDate.of(year, dto.getMonth(), 1);
        card.setExpireDate(expireDate);
        cardRepository.save(card);
        return card;
    }

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
