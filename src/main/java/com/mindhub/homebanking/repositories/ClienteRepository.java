package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClienteRepository extends JpaRepository<Cliente,Long> {

  Cliente findByEmail(String email);

}
