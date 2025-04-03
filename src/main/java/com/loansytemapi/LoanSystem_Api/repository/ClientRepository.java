package com.loansytemapi.LoanSystem_Api.repository;

import com.loansytemapi.LoanSystem_Api.model.Client;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class ClientRepository {

    private final Map<String, Client> clients = new HashMap<>();

    public List<Client> findAll() {
        return new ArrayList<>(this.clients.values());
    }

    public Client findById(String id) {
        return this.clients.get(id);
    }

    public Client save(Client client) {
        this.clients.put(client.getId(), client);
        return client;
    }

    public Client delete(String id) {
        Client client = this.clients.get(id);
        this.clients.remove(id);
        return client;
    }

    public Client update(String id, Client client) {
        this.clients.put(id, client);
        return client;
    }

    public List<Client> filterByQuery(String query) {
        if (query == null || query.isEmpty()) {
            return new ArrayList<>(this.clients.values());
        }

        String lowerCaseQuery = query.toLowerCase();

        return this.clients.values().stream()
                .filter(client -> {
                    return (client.getId() != null && client.getId().toLowerCase().contains(lowerCaseQuery)) ||
                            (client.getFirstName() != null && client.getFirstName().toLowerCase().contains(lowerCaseQuery)) ||
                            (client.getSecondName() != null && client.getSecondName().toLowerCase().contains(lowerCaseQuery)) ||
                            (client.getEmail() != null && client.getEmail().toLowerCase().contains(lowerCaseQuery)) ||
                            (client.getPhone() != null && client.getPhone().toLowerCase().contains(lowerCaseQuery));
                })
                .collect(Collectors.toList());
    }
}
