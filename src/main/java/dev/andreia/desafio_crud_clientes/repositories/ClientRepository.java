package dev.andreia.desafio_crud_clientes.repositories;

import dev.andreia.desafio_crud_clientes.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
