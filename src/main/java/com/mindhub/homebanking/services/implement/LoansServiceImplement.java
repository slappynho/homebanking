package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoansServiceImplement implements LoansService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Override
    public Loan findById(long id) {
        return loanRepository.findById(id).orElse(null);
    }



    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }
}
