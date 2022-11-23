package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.DTO.ClienteDTO;
import com.mindhub.homebanking.models.Cliente;
import com.mindhub.homebanking.repositories.ClienteRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    ClienteRepository clienteRepository;


    @Override
    public List<ClienteDTO> getClientesDTO() {
        return clienteRepository.findAll().stream().map(cliente -> new ClienteDTO(cliente)).collect(toList());
    }

    @Override
    public ClienteDTO getClienteDTO(long id) {
        return clienteRepository.findById(id).map(cliente -> new ClienteDTO(cliente)).orElse(null);
    }

    @Override
    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Cliente cliente) {
        clienteRepository.save(cliente);
    }


}
