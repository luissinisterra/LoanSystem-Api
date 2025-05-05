package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.model.Client;
import com.loansytemapi.LoanSystem_Api.repository.imp.IClientRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Client getClientById(String id) {
        return iClientRepository.findById(id).orElse(null);
    }

    @Override
    public Client createClient(Client client) throws IncompleteDataException {
        if (validateFields(client)) {
            return iClientRepository.save(client);
        }
        throw new IncompleteDataException("Por favor complete todos los campos obligatorios.");
    }

    @Override
    public Client deleteClient(String id) {
        Client client = getClientById(id);
        if (client != null) {
            iClientRepository.deleteById(id);
        }
        return client;
    }

    @Override
    public Client updateClient(String id, Client updatedClient) {
        updatedClient.setId(id);
        return iClientRepository.save(updatedClient);
    }

    @Override
    public List<Client> searchClientsByQuery(String query) {
        return iClientRepository.searchClientsByQuery(query);
    }

    private boolean validateFields(Client client) {
        return client.getId() != null && !client.getId().isEmpty() &&
                client.getFirstName() != null && !client.getFirstName().isEmpty() &&
                client.getFirstSurname() != null && !client.getFirstSurname().isEmpty() &&
                client.getAge() > 0 &&
                client.getEmail() != null && !client.getEmail().isEmpty() &&
                client.getPhone() != null && !client.getPhone().isEmpty() &&
                client.getAddress() != null &&
                client.getAddress().getCountry() != null && !client.getAddress().getCountry().isEmpty() &&
                client.getAddress().getDeparment() != null && !client.getAddress().getDeparment().isEmpty() &&
                client.getAddress().getCity() != null && !client.getAddress().getCity().isEmpty() &&
                client.getAddress().getStreet() != null && !client.getAddress().getStreet().isEmpty() &&
                client.getAddress().getPostalCode() != null && !client.getAddress().getPostalCode().isEmpty();
    }
}