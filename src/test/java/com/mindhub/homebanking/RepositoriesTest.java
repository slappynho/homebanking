package com.mindhub.homebanking;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    AccountRepository accountRepository;


    @MockBean
    PasswordEncoder passwordEncoder;

    @Test

    public void existLoans(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans,is(not(empty())));

    }


    @Test

    public void existPersonalLoan(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));

    }

    @Test

    public void existClient(){

        List<Cliente> clients = clienteRepository.findAll();

        assertThat(clients,is(not(empty())));

    }


    @Test

    public void existClientName(){

        List<Cliente> clients = clienteRepository.findAll();

        assertThat(clients, hasItem(hasProperty("firstName", is("Melba"))));

    }


    @Test

    public void existTransaction(){

        List<Transaction> transactions = transactionRepository.findAll();

        assertThat(transactions,is(not(empty())));

    }


    @Test

    public void existTransactionAmount(){

        List<Transaction> transactions = transactionRepository.findAll();

        assertThat(transactions, hasItem(hasProperty("description", is("Salario"))));


    }


    @Test

    public void existAccount(){

        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts,is(not(empty())));

    }


    @Test

    public void existAccountNumber(){

        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts, hasItem(hasProperty("number", is("VIN-001"))));
    }


    @Test

    public void existCards(){

        List<Card> cards = cardRepository.findAll();

        assertThat(cards,is(not(empty())));

    }


    @Test

    public void existCardHolder(){

        List<Card> cards = cardRepository.findAll();

        assertThat(cards, hasItem(hasProperty("cardHolder", is("Melba Morel"))));
    }













}
