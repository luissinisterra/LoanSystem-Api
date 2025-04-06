package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.model.Address;
import com.loansytemapi.LoanSystem_Api.model.Client;
import com.loansytemapi.LoanSystem_Api.model.Loan;
import com.loansytemapi.LoanSystem_Api.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
                "L001",               // ID del préstamo
                null,                 // Aquí iría el cliente, se asignará después cuando lo usemos en Client
                15000.00,             // Monto del préstamo
                LocalDate.of(2025, 3, 15)  // Fecha del préstamo (15 de marzo de 2025)
        );

        Map<String, Loan> loans = new HashMap<>();
        loans.put(loan.getId(), loan);  // Añadimos el préstamo al mapa

        // Ahora creamos el cliente, pasándole los datos
        Client client = new Client("P12345", "Juan", "Carlos", "García", "López", 30,
                "juan.garcia@example.com", "600123456", address);

        this.clientRepository.createClient(client);
    }

    public List<Client> findAll() {
        return this.clientRepository.getAllClients();
    }

    public Client findById(String id) {
        return this.clientRepository.getClientById(id);
    }

    public Client save(Client client) {
        return this.clientRepository.createClient(client);
    }

    public Client delete(String id) {
        return this.clientRepository.deleteClient(id);
    }

    public Client update(String id, Client client) {
        return this.clientRepository.updateClient(id, client);
    }

    public List<Client> filterByQuery(String query) {
        return this.clientRepository.searchClientsByQuery(query);
    }
}
