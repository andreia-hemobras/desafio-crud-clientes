package dev.andreia.desafio_crud_clientes.services;

import dev.andreia.desafio_crud_clientes.dto.ClientDto;
import dev.andreia.desafio_crud_clientes.entities.Client;
import dev.andreia.desafio_crud_clientes.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public Page<ClientDto> findAll(Pageable pageable){
        Page<Client> page = repository.findAll(pageable);
        return page.map(ClientDto::new);
    }
}
