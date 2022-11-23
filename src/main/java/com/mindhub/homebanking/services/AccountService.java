package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Cliente;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface AccountService {

    public void saveAccount(Account account);

    public List<AccountDTO> getAccountsDTO();

    public AccountDTO getAccount(Long id);

    public Account findByNumber(String number);

}
