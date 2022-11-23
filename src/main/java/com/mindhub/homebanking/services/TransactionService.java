package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionService {

    public void saveTransaction(Transaction transaction);

    public List<TransactionDTO> getTransactions();

    public TransactionDTO getTransaction(Long id);

}
