package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.model.Client;
import java.util.List;

public interface IClientService {
    List<Client> getAllClients();
    Client getClientById(int id);
    Client createClient(Client client) throws IncompleteDataException;
    Client updateClient(int id, Client client);
    Client deleteClient(int id);
    List<Client> searchClientsByQuery(String query);
}
