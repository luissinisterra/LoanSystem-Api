package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.model.Client;
import com.loansytemapi.LoanSystem_Api.repository.IClientRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService implements IClientService {

    private final IClientRepository iClientRepository;

    @Autowired
    public ClientService(IClientRepository iClientRepository) {
        this.iClientRepository = iClientRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return iClientRepository.findAll();
    }

    @Override
    public Client getClientById(int id) {
        return iClientRepository.findById(id).orElse(null);
    }

    @Override
    public Client createClient(Client client) throws IncompleteDataException {
        return iClientRepository.save(client);
    }

    @Override
    public Client deleteClient(int id) {
        Client client = getClientById(id);
        if (client != null) {
            iClientRepository.deleteById(id);
        }
        return client;
    }

    @Override
    public Client updateClient(int id, Client updatedClient) {
        //updatedClient.setId(id);
        return iClientRepository.save(updatedClient);
    }

    @Override
    public List<Client> searchClientsByQuery(String query) {
        //return iClientRepository.searchClientsByQuery(query);
        return new ArrayList<>();
    }

}