package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.DTO.ClientLoanDTO;
import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.TransactionManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoansController {

    @Autowired
    AccountService accountService;

    @Autowired
    ClientLoanService clientLoanService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    LoansService loansService;

    @Autowired
    ClientService clientService;

    @RequestMapping("/api/loans")
    public List<LoanDTO> loanDTOS() {
        return loansService.getLoans();
    }

    @PostMapping("/api/admin/loans")
    public ResponseEntity<String> addAdminLoan (@RequestParam String name, @RequestParam Double amount, @RequestParam List<Integer> payments, Authentication authentication){

        Cliente adminAuthentication = clientService.findByEmail(authentication.getName());

        if(adminAuthentication == null){
            return new ResponseEntity<>("No estas autenticado", HttpStatus.FORBIDDEN);
        }

        if(name.isEmpty()){
            return new ResponseEntity<>("Falta el nombre del prestamo", HttpStatus.FORBIDDEN);
        }

        if(amount <= 0){
            return new ResponseEntity<>("Falta el monto del prestamo", HttpStatus.FORBIDDEN);
        }

        if(loansService.getLoans().stream().map(x -> x.getName()).collect(Collectors.toSet()).contains(name)){
            return new ResponseEntity<>("Ya tienes este prestamo", HttpStatus.FORBIDDEN);
        }

        //loanServices.saveLoan(new Loan(name, amount, payments));
        Loan loan = new Loan(amount,name,payments);
        //loanServices.saveLoan(loan);
        loansService.saveLoan(loan);

        return new ResponseEntity<>("Prestamo creado",HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/api/loans")
    public ResponseEntity<Object> addLoan (Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){
        Cliente currentClient = clientService.findByEmail(authentication.getName());
        Account destinAcc = accountService.findByNumber(loanApplicationDTO.getAccountDestin());
        Loan loanExists = loansService.findById(loanApplicationDTO.getId());

        if (currentClient != null){
            if (loanApplicationDTO.getAmount() <= 0){
                return new ResponseEntity<>("Monto incorrecto", HttpStatus.FORBIDDEN);
            }
            if(loanApplicationDTO.getAccountDestin().isEmpty()){
                return new ResponseEntity<>("Cuenta de destino no encontrada", HttpStatus.FORBIDDEN);
            }
            if (destinAcc == null){
                return new ResponseEntity<>("Cuenta de destino no encontrada", HttpStatus.FORBIDDEN);
            }
            if(loanApplicationDTO.getPayments() == null){
                return new ResponseEntity<>("Las cuotas ingresadas no corresponden con las predefinidas", HttpStatus.FORBIDDEN);
            }
            if (currentClient.getClientLoans().stream().filter(clientLoan -> clientLoan.getLoan().getName().equals(loanExists.getName())).toArray().length == 1) {
                return new ResponseEntity<>("Ya posees un prestamo de este tipo", HttpStatus.FORBIDDEN);
            }
            if (loanExists == null){
                return new ResponseEntity<>("El préstamo no existe", HttpStatus.FORBIDDEN);
            }
            if(loanApplicationDTO.getAmount() > loanExists.getMaxAmount()){
                return new ResponseEntity<>("El monto ingresado excede el maximo posible", HttpStatus.FORBIDDEN);
            }
            if (!loanExists.getPayments().contains(loanApplicationDTO.getPayments())){
                return new ResponseEntity<>("Las cuotas ingresadas no corresponden con las predefinidas", HttpStatus.FORBIDDEN);
            }
            if (!currentClient.getAccounts().contains(destinAcc)){
                return new ResponseEntity<>("La cuenta no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
            }

            ClientLoan loan = new ClientLoan(currentClient, loanExists,loanApplicationDTO.getPayments(),loanApplicationDTO.getAmount() * 1.15);
            clientLoanService.saveClientLoan(loan);

            switch (loanExists.getName()){
                case "Personal":
                    switch (loanApplicationDTO.getPayments()){
                        case 6: loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.10));
                            break;
                        case 12: loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.20));
                            break;
                        case 24: loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.30));
                            break;
                    }
                    break;
                case "Hipotecario":
                    switch (loanApplicationDTO.getPayments()){
                        case 6: loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.10));
                            break;
                        case 12: loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.20));
                            break;
                        case 24: loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.30));
                            break;
                        case 36: loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.40));
                            break;
                        case 48: loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.50));
                            break;
                        case 60: loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.60));
                            break;
                    }

                    break;
                case "Vehiculo":
                    switch (loanApplicationDTO.getPayments()){
                        case 6: loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.10));
                            break;
                        case 12: loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.20));
                            break;
                        case 24: loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.30));
                            break;
                        case 36: loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.40));
                            break;
                    }
                    break;
                default:
                    break;

            }


            Transaction transaction = new Transaction(destinAcc, TransactionType.CREDIT,loanApplicationDTO.getAmount(),"Prestamo "+loanExists.getName()+"aprobado!", LocalDateTime.now(),destinAcc.getBalance());
            destinAcc.setBalance(destinAcc.getBalance()+loanApplicationDTO.getAmount());
            accountService.saveAccount(destinAcc);
            transactionService.saveTransaction(transaction);

            return new ResponseEntity<>("Prestamo hecho!",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Debes estar autenticado",HttpStatus.FORBIDDEN);
    }
}
