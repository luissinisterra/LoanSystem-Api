package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.model.Client;
import java.util.List;

public interface IClientService {
    List<Client> getAllClients();
    Client getClientById(String id);
    Client createClient(Client client);
    Client updateClient(String id, Client client);
    Client deleteClient(String id);
    List<Client> searchClientsByQuery(String query);
}
