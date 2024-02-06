package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.Card;
import finalproject.finalproject.Entity.user.Customer;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
public class CardServiceImpl
        implements CardService {
    private final CardRepository cardRepository;
    private final CustomerServiceImpl customerService;

    public void creatCard(Customer customer, CardDtoRequest cardDtoRequest) {
        Card card = new Card();
        Card.builder()
                .cardNumber(cardDtoRequest.getCardNumber())
                .cvv2(cardDtoRequest.getCvv2())
                .password(cardDtoRequest.getPassword())
                .expireDate(LocalDate.of(cardDtoRequest.getYear(), cardDtoRequest.getMonth(), 00))
                .build();
        cardRepository.save(card);
        customer.setCard(Collections.singletonList(card));
        customerService.save(customer);
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
