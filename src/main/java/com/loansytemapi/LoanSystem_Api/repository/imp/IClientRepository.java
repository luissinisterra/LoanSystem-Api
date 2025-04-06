package com.loansytemapi.LoanSystem_Api.repository.imp;

import com.loansytemapi.LoanSystem_Api.model.Client;
import java.util.List;

public interface IClientRepository {
    List<Client> getAllClients();
    Client getClientById(String id);
    Client createClient(Client client);
    Client updateClient(String id, Client client);
    Client deleteClient(String id);
    List<Client> searchClientsByQuery(String query);
}
