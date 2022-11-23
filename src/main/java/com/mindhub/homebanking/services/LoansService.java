package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;
import java.util.Optional;

public interface LoansService {

    public List<LoanDTO> getLoans();

    public Loan findById (long id);

    public void saveLoan(Loan loan);

}
