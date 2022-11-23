package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTO.ClienteDTO;
import com.mindhub.homebanking.models.Cliente;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService  {

    public List<ClienteDTO> getClientesDTO();

    public ClienteDTO getClienteDTO(long id);

    public Cliente findByEmail(String email);

    public void saveClient(Cliente cliente);
}
