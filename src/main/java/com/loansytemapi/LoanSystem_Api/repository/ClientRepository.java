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
        List<Client> clients = new ArrayList<>();
        return this.clients.values().stream().
                filter(client -> query == null || client.getId().contains(query))
                .collect(Collectors.toUnmodifiableList());
    }

}
