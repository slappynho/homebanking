package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    Set<Card> cards = new HashSet<>();

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    Set<ClientLoan> clientLoans = new HashSet<>();

    public Cliente() {
    }

    public Cliente(String firstName, String lastName, String email,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password=password;
    }

    public long getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccounts() { return accounts; }

    public Set<Card> getCards() {
        return cards;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addAccount (Account account) {
        account.setCliente(this);
        accounts.add(account);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
