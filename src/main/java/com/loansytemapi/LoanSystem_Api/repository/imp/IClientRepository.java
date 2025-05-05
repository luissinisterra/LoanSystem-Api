package com.loansytemapi.LoanSystem_Api.repository.imp;

import com.loansytemapi.LoanSystem_Api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClientRepository extends JpaRepository<Client, String> {

    default Client createClient(Client client) {
        return save(client);
    }

    default List<Client> getAllClients() {
        return findAll();
    }

    default Client getClientById(String id) {
        Optional<Client> optionalClient = findById(id);
        return optionalClient.orElse(null);
    }

    default Client deleteClient(String id) {
        Optional<Client> optionalClient = findById(id);
        if (optionalClient.isPresent()) {
            deleteById(id);
        }
        return optionalClient.orElse(null);
    }

    default Client updateClient(String id, Client updatedClient) {
        updatedClient.setId(id);
        return save(updatedClient);
    }

    @Query("SELECT c FROM Client c WHERE " +
            "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(c.firstSurname) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(c.id) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Client> searchClientsByQuery(@Param("query") String query);
}
