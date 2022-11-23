package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.DTO.ClienteDTO;
import com.mindhub.homebanking.configurations.WebAuthentication;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ClientController{


    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping("/api/clientes")
    public List<ClienteDTO> getClientes(){
        return clientService.getClientesDTO();
    }


    @RequestMapping("/api/clientes/{id}")
    public ClienteDTO getCliente(@PathVariable long id){
        return clientService.getClienteDTO(id);
    }


    @RequestMapping("/api/clientes/current")
    public ClienteDTO currentClient(Authentication authentication) {
        return new ClienteDTO(clientService.findByEmail(authentication.getName()));

    }
    public int getRandomNumber (int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }




    @PostMapping("/api/clientes")
    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {


        if (firstName == null){
            return new ResponseEntity<>("Esta faltando su(s) nombre(s)", HttpStatus.FORBIDDEN);
        }
        if (lastName.isEmpty()){
            return new ResponseEntity<>("Esta faltando su(s) apellido(s)", HttpStatus.FORBIDDEN);
        }
        if (email.isEmpty()){
            return new ResponseEntity<>("Esta faltando el email", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()) {
            return new ResponseEntity<>("Esta faltando la contraseña", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) != null) {
            return new ResponseEntity<>("Ya hay un cliente registrado con ese email", HttpStatus.FORBIDDEN);

        }

        Cliente newClient=new Cliente(firstName,lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(newClient);
        Account newAccount= new Account ( "VIN-" + getRandomNumber(11111111, 99999999), 0.0, LocalDateTime.now(), true);
        newClient.addAccount(newAccount);
        accountService.saveAccount(newAccount);
        return new ResponseEntity<>(newClient,HttpStatus.CREATED);
    }


}
