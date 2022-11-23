package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Cliente;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClienteRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;


    @RequestMapping("/api/transactions")
    private List<TransactionDTO> getTransactions() {
        return transactionService.getTransactions();

    }

    @RequestMapping("/api/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) {
        return transactionService.getTransaction(id);
    }

    @Transactional
    @PostMapping("/api/transactions")
    public ResponseEntity<Object> createTransaction (Authentication authentication,
         @RequestParam double amount, @RequestParam String description, @RequestParam String accountO, @RequestParam String accountD) {

        Cliente currentClient = clientService.findByEmail(authentication.getName());
        Account accountOrigin = accountService.findByNumber(accountO);
        Account accountDestin = accountService.findByNumber(accountD);
        Set<Account> accountExist = currentClient.getAccounts().stream().filter(account -> account.getNumber().equals(accountO)).collect(Collectors.toSet());



        if (amount <= 0) {
            return new ResponseEntity<>("Falta el Monto", HttpStatus.EXPECTATION_FAILED);
        }
        if (description.isEmpty()) {
            return new ResponseEntity<>("Falta la descripcion", HttpStatus.FORBIDDEN);
        }
        if (accountO.isEmpty() ) {
            return new ResponseEntity<>("Falta la cuenta origen", HttpStatus.FORBIDDEN);
        }
        if (accountD.isEmpty()) {
            return new ResponseEntity<>("Falta la cuenta destino", HttpStatus.FORBIDDEN);
        }

        if (accountO.equals(accountD)){
            return new ResponseEntity<>("La cuenta origen no puede ser igual a la de destino", HttpStatus.FORBIDDEN);
        }

        if(accountExist.isEmpty()){
            return new ResponseEntity<>("No se pudo obtener la cuenta", HttpStatus.FORBIDDEN);
        }

        if(accountDestin == null){
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }

        if(accountOrigin.getBalance() <= amount){
            return new ResponseEntity<>("No tienes fondos suficientes", HttpStatus.FORBIDDEN);
        }
        accountOrigin.setBalance(accountOrigin.getBalance() + amount);
        accountDestin.setBalance(accountDestin.getBalance() + amount);
        Transaction transactionOrigin = new Transaction(accountOrigin,TransactionType.DEBIT, -amount, description + " a " + accountDestin.getNumber(), LocalDateTime.now(),accountOrigin.getBalance());
        Transaction transactionDestin = new Transaction(accountDestin,TransactionType.CREDIT, +amount, description + " de " + accountOrigin.getNumber(), LocalDateTime.now(),accountDestin.getBalance());
        transactionService.saveTransaction(transactionOrigin);
        transactionService.saveTransaction(transactionDestin);
        accountService.saveAccount(accountOrigin);
        accountService.saveAccount(accountDestin);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}


