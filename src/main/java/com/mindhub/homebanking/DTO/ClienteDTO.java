package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Cliente;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClienteDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<CardDTO> cards = new HashSet<>();
    private Set<ClientLoanDTO> loans = new HashSet<>();
    private Set<AccountDTO> accounts = new HashSet<>();

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId(); ;
        this.firstName = cliente.getFirstName();
        this.lastName = cliente.getLastName();
        this.email = cliente.getEmail();
        this.accounts = cliente.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        this.loans = cliente.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
        this.cards = cliente.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() { return accounts; }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}



