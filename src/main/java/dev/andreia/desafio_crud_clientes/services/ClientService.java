package dev.andreia.desafio_crud_clientes.services;

import dev.andreia.desafio_crud_clientes.dto.ClientDto;
import dev.andreia.desafio_crud_clientes.entities.Client;
import dev.andreia.desafio_crud_clientes.repositories.ClientRepository;
import dev.andreia.desafio_crud_clientes.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDto> findAll(Pageable pageable){
        Page<Client> page = repository.findAll(pageable);
        return page.map(ClientDto::new);
    }

    @Transactional(readOnly = true)
    public ClientDto findById(Long id){
        Client client = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado."));

        return new ClientDto(client);
    }

    @Transactional
    public ClientDto insert(ClientDto dto){
        Client entity = new Client();
        copyDtoToEntity(dto, entity);
        repository.save(entity);

        return new ClientDto(entity);
    }

    @Transactional
    public ClientDto update(Long id, ClientDto dto){
        try{
            Client entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            repository.save(entity);

            return new ClientDto(entity);
        } catch(EntityNotFoundException ex){
            throw new ResourceNotFoundException("Recurso não encontrado.");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado.");
        }

        repository.deleteById(id);
    }

    private void copyDtoToEntity(ClientDto dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}
