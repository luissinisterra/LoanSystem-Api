package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.dto.ClientDTO;
import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Client;
import com.loansytemapi.LoanSystem_Api.model.Loan;
import com.loansytemapi.LoanSystem_Api.model.User;
import com.loansytemapi.LoanSystem_Api.repository.IClientRepository;
import com.loansytemapi.LoanSystem_Api.repository.ILoanRepository;
import com.loansytemapi.LoanSystem_Api.repository.IUserRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.IClientService;
import com.loansytemapi.LoanSystem_Api.service.imp.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService implements IClientService {

    private final IClientRepository iClientRepository;
    private final IUserRepository iUserRepository;
    private final ILoanRepository iLoanRepository;

    @Autowired
    public ClientService(IClientRepository iClientRepository, IUserRepository iUserRepository, ILoanRepository iLoanRepository) {
        this.iClientRepository = iClientRepository;
        this.iUserRepository = iUserRepository;
        this.iLoanRepository = iLoanRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return iClientRepository.findAll();
    }

    @Override
    public List<Client> getAllClientsByUserId(int userId) {
        return iClientRepository.findAllByUser_Id(userId);
    }

    @Override
    public Client getClientById(int id) throws NotFoundException {
        return iClientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con ID: " + id));
    }

    @Override
    public Client createClient(ClientDTO client) throws IncompleteDataException, NotFoundException {

        User user = this.iUserRepository.findById(client.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Client newClient = new Client();
        newClient.setId(client.getUserId());
        newClient.setFirstName(client.getFirstName());
        newClient.setSecondName(client.getSecondName());
        newClient.setFirstSurname(client.getFirstSurname());
        newClient.setSecondSurname(client.getSecondSurname());
        newClient.setAge(client.getAge());
        newClient.setEmail(client.getEmail());
        newClient.setPhone(client.getPhone());
        newClient.setActive(client.isActive());
        newClient.setAddress(client.getAddress());
        newClient.setUser(user);

        if (client.getFirstName() == null || client.getFirstName().trim().isEmpty()) {
            throw new IncompleteDataException("El primer nombre es obligatorio.");
        }
        if (client.getFirstSurname() == null || client.getFirstSurname().trim().isEmpty()) {
            throw new IncompleteDataException("El primer apellido es obligatorio.");
        }
        if (client.getEmail() == null || client.getEmail().trim().isEmpty()) {
            throw new IncompleteDataException("El correo electrónico es obligatorio.");
        }
        if (client.getPhone() == null || client.getPhone().trim().isEmpty()) {
            throw new IncompleteDataException("El teléfono es obligatorio.");
        }
        if (user == null) {
            throw new IncompleteDataException("El usuario asociado es obligatorio.");
        }

        return iClientRepository.save(newClient);
    }

    @Override
    public Client updateClient(int id, ClientDTO updatedClient) throws IncompleteDataException, NotFoundException {

        User user = this.iUserRepository.findById(updatedClient.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Client existingClient = getClientById(id);

        existingClient.setFirstName(updatedClient.getFirstName());
        existingClient.setSecondName(updatedClient.getSecondName());
        existingClient.setFirstSurname(updatedClient.getFirstSurname());
        existingClient.setSecondSurname(updatedClient.getSecondSurname());
        existingClient.setAge(updatedClient.getAge());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setPhone(updatedClient.getPhone());
        existingClient.setActive(updatedClient.isActive());
        existingClient.setAddress(updatedClient.getAddress());
        existingClient.setUser(user);

        // Validar datos actualizados
        if (existingClient.getFirstName() == null || existingClient.getFirstName().trim().isEmpty()) {
            throw new IncompleteDataException("El primer nombre es obligatorio.");
        }
        if (existingClient.getFirstSurname() == null || existingClient.getFirstSurname().trim().isEmpty()) {
            throw new IncompleteDataException("El primer apellido es obligatorio.");
        }
        if (existingClient.getEmail() == null || existingClient.getEmail().trim().isEmpty()) {
            throw new IncompleteDataException("El correo electrónico es obligatorio.");
        }
        if (existingClient.getPhone() == null || existingClient.getPhone().trim().isEmpty()) {
            throw new IncompleteDataException("El teléfono es obligatorio.");
        }
        if (existingClient.getUser() == null) {
            throw new IncompleteDataException("El usuario asociado es obligatorio.");
        }

        return iClientRepository.save(existingClient);
    }

    @Override
    public Client deleteClient(int id) throws NotFoundException {

        Client client = iClientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Eliminar los préstamos asociados al cliente
        List<Loan> loans = iLoanRepository.findAllByUser_Id(id);
        for (Loan loan : loans) {
            iLoanRepository.delete(loan);
        }

        iClientRepository.deleteById(id);
        return client;
    }

    @Override
    public List<Client> searchClientsByQuery(int userId, String query) {
        if (query == null || query.trim().isEmpty()) {
            return iClientRepository.findAll();
        }

        String queryLower = query.toLowerCase().trim();

        return this.iClientRepository.findAllByUser_Id(userId).stream()
                .filter(client -> String.valueOf(client.getId()).toLowerCase().contains(queryLower) ||
                                client.getFirstName().toLowerCase().contains(queryLower) ||
                                client.getSecondName().toLowerCase().contains(queryLower) ||
                                client.getFirstSurname().toLowerCase().contains(queryLower) ||
                                client.getSecondSurname().toLowerCase().contains(queryLower) ||
                                client.getEmail().toLowerCase().contains(queryLower) ||
                                client.getPhone().toLowerCase().contains(queryLower) ||
                                client.getAddress().toLowerCase().contains(queryLower)).toList();
    }
}