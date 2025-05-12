package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Client;
import java.util.List;

public interface IClientService {
    List<Client> getAllClients();
    Client getClientById(int id) throws NotFoundException;
    Client createClient(Client client) throws IncompleteDataException;
    Client updateClient(int id, Client client) throws IncompleteDataException, NotFoundException;
    Client deleteClient(int id) throws NotFoundException;
    List<Client> searchClientsByQuery(String query);
}
