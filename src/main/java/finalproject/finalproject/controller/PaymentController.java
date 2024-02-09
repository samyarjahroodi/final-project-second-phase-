package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.payment.Card;
import finalproject.finalproject.repository.CardRepository;
import finalproject.finalproject.service.dto.request.CardSearchRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class PaymentController {

    private final CardRepository cardRepository;

    @PostMapping("/search")
    public ResponseEntity<?> searchCard(@RequestBody CardSearchRequest request) {
        Optional<Card> card = cardRepository.findCardByCardNumberAndCvv2AndPassword(
                request.getBankAccountNumber(),
                Integer.parseInt(request.getCvv2()),
                request.getPassword()
        );

        if(card.isPresent()) {
            return ResponseEntity.ok().body("Card found, payment processed.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found.");
        }
    }
}