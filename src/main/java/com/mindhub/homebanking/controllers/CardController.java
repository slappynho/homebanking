package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Cliente;

import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.cardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
public class CardController {


    @Autowired
    ClientService clientService;

    @Autowired
    CardService cardService;



    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    @PostMapping("/api/clientes/current/cards")
    public ResponseEntity<Object> createCards(
            Authentication authentication, @RequestParam CardType type, @RequestParam CardColor color ){

        int cardCvv = cardUtils.getCardCvv();

        Cliente currentClient = clientService.findByEmail(authentication.getName());

        String cardNumber= cardUtils.getCardNumber();

        if (type == null) {
            return new ResponseEntity<>("Debe elegir un tipo (DEBITO/CREDITO) de tarjeta",HttpStatus.FORBIDDEN);
        }
        if (color == null) {
            return new ResponseEntity<>("Debe elegir el color (TITANIO/PLATA/ORO) de la tarjeta",HttpStatus.FORBIDDEN);
        }

        if (currentClient.getCards().stream().filter(card -> card.getType().equals(type)).count() >= 3) {
            return new ResponseEntity<>("Solo puedes tener 3 tarjetas de"+type, HttpStatus.FORBIDDEN);
        }
        cardService.saveCard(new Card(currentClient,currentClient.getFirstName()+" "+currentClient.getLastName(),type,color,cardNumber,cardCvv, LocalDateTime.now(),LocalDateTime.now().plusYears(5), true));
        return new ResponseEntity<>("Tarjeta creada",HttpStatus.CREATED);

    }

    @PatchMapping("/api/clientes/current/cards/state")
    public ResponseEntity<?> updateCardState(Authentication authentication, @RequestParam String number) {
        Cliente clientCard = clientService.findByEmail(authentication.getName());
        Card card = cardService.findByNumber(number);

        if (card == null) {
            return new ResponseEntity<>("Card not found", HttpStatus.NOT_FOUND);
        }
        if(clientCard == null){
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }
        if (number.isEmpty()){
            return new ResponseEntity<>("Card number is empty", HttpStatus.BAD_REQUEST);
        }
        if (!clientCard.getCards().contains(card)) {
            return new ResponseEntity<>("You can't update this card", HttpStatus.FORBIDDEN);
        }
        card.setActive(false);
        cardService.saveCard(card);
        return new ResponseEntity<>("Tarjeta actualizada", HttpStatus.OK);
    }

}
