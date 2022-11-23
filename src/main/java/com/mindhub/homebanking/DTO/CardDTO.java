package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDateTime;

public class CardDTO {

    private long id;

    private String cardHolder;

    private CardType type;

    private CardColor color;

    private String number;

    private Integer cvv;

    private LocalDateTime thruDate;

    private LocalDateTime fromDate;

    private boolean active;

    public CardDTO() {

    }

    public CardDTO(Card card){
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.type = card.getType();
        this.color=card.getColor();
        this.number=card.getNumber();
        this.cvv= card.getCvv();
        this.thruDate=card.getThruDate();
        this.fromDate=card.getFromDate();
        this.active= card.isActive();
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public long getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }
}
