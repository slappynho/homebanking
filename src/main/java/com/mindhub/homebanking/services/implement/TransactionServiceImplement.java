package com.mindhub.homebanking.services.implement;


import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplement implements TransactionService {


    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDTO> getTransactions() {
        return transactionRepository.findAll().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
    }

    @Override
    public TransactionDTO getTransaction(Long id) {
        return transactionRepository.findById(id).map(transaction -> new TransactionDTO(transaction)).orElse(null);
    }


}
