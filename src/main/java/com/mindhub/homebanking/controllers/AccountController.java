package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.ClienteDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Cliente;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClienteRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;

    @RequestMapping("/api/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccountsDTO();
    }

    @RequestMapping("/api/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @RequestMapping("/api/accounts/current")
    public ClienteDTO getAuthenticatedClient(Authentication authentication) {
        return new ClienteDTO(clientService.findByEmail(authentication.getName()));

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    @PostMapping("/api/clientes/current/accounts")
    public ResponseEntity<Object> currentAccount(Authentication authentication) {
        Cliente currentClient = clientService.findByEmail(authentication.getName());
        if (currentClient.getAccounts().size() >= 3) {
            return new ResponseEntity<>("No puedes crear mas de 3 cuentas", HttpStatus.FORBIDDEN);
        } else {
            Account newAccount= new Account ("VIN-" + getRandomNumber(11111111, 99999999), 0.0, LocalDateTime.now(), true);
            currentClient.addAccount(newAccount);
            accountService.saveAccount(newAccount);
            return new ResponseEntity<>("Cuenta creada exitosamente", HttpStatus.CREATED);
        }
    }
}