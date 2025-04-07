package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.model.Address;
import com.loansytemapi.LoanSystem_Api.model.Client;
import com.loansytemapi.LoanSystem_Api.model.Loan;
import com.loansytemapi.LoanSystem_Api.repository.ClientRepository;
import com.loansytemapi.LoanSystem_Api.repository.imp.IClientRepository;
import com.loansytemapi.LoanSystem_Api.repository.imp.ILoanRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientService implements IClientService {
    private final IClientRepository iClientRepository;

    @Autowired
    public ClientService(IClientRepository iClientRepository) {
        this.iClientRepository = iClientRepository;
        this.initSampleData();
    }

    public void initSampleData(){
        Address address = new Address(
                "España",           // País
                "Madrid",           // Departamento o provincia
                "Madrid",           // Ciudad
                "Calle Mayor, 25",  // Calle
                "28001"             // Código Postal
        );

        Loan loan = new Loan(
                null,                 // Aquí iría el cliente, se asignará después cuando lo usemos en Client
                15000.00,             // Monto del préstamo
                123,
                123123,
                LocalDate.of(2025, 3, 15)  // Fecha del préstamo (15 de marzo de 2025)
        );

        Map<String, Loan> loans = new HashMap<>();
        loans.put(loan.getId(), loan);  // Añadimos el préstamo al mapa

        // Ahora creamos el cliente, pasándole los datos
        Client client = new Client("P12345", "Juan", "Carlos", "García", "López", 30,
                "juan.garcia@example.com", "600123456", address);

        this.iClientRepository.createClient(client);
    }

    @Override
    public List<Client> getAllClients() {
        return this.iClientRepository.getAllClients();
    }

    @Override
    public Client getClientById(String id) {
        return this.iClientRepository.getClientById(id);
    }

    @Override
    public Client createClient(Client client) {
        return this.iClientRepository.createClient(client);
    }

    @Override
    public Client deleteClient(String id) {
        return this.iClientRepository.deleteClient(id);
    }

    @Override
    public Client updateClient(String id, Client client) {
        return this.iClientRepository.updateClient(id, client);
    }

    @Override
    public List<Client> searchClientsByQuery(String query) {
        return this.iClientRepository.searchClientsByQuery(query);
    }
}
