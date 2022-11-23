package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;

public interface CardService {

    public void saveCard(Card card);

    public Card findByNumber(String number);
}
