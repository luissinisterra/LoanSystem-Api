package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.dto.ClientDTO;
import com.loansytemapi.LoanSystem_Api.dto.ClientResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Client;
import com.loansytemapi.LoanSystem_Api.model.Loan;
import com.loansytemapi.LoanSystem_Api.model.User;
import com.loansytemapi.LoanSystem_Api.repository.IClientRepository;
import com.loansytemapi.LoanSystem_Api.repository.ILoanRepository;
import com.loansytemapi.LoanSystem_Api.repository.IUserRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.IClientService;
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
    public List<ClientResponseDTO> getAllClients() {
        List<Client> all = iClientRepository.findAll();
        List<ClientResponseDTO> allDTO = new ArrayList<>();

        for (Client client : all) {
            allDTO.add(new ClientResponseDTO(client));
        }

        return allDTO;
    }

    @Override
    public List<ClientResponseDTO> getAllClientsByUserId(int userId) {
        List<Client> all = iClientRepository.findAllByUser_Id(userId);
        List<ClientResponseDTO> allDTO = new ArrayList<>();

        for (Client client : all) {
            allDTO.add(new ClientResponseDTO(client));
        }

        return allDTO;
    }

    @Override
    public ClientResponseDTO getClientById(int id) throws NotFoundException {
        return new ClientResponseDTO(iClientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con ID: " + id)));
    }

    @Override
    public ClientResponseDTO createClient(ClientDTO client) throws IncompleteDataException, NotFoundException {

        User user = this.iUserRepository.findById(client.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Client newClient = new Client();
        newClient.setId(client.getId());
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

        return new ClientResponseDTO(iClientRepository.save(newClient));
    }

    @Override
    public ClientResponseDTO updateClient(int id, ClientDTO updatedClient) throws IncompleteDataException, NotFoundException {

        User user = this.iUserRepository.findById(updatedClient.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Client client = new Client();
        client.setFirstName(updatedClient.getFirstName());
        client.setSecondName(updatedClient.getSecondName());
        client.setFirstSurname(updatedClient.getFirstSurname());
        client.setSecondSurname(updatedClient.getSecondSurname());
        client.setAge(updatedClient.getAge());
        client.setEmail(updatedClient.getEmail());
        client.setPhone(updatedClient.getPhone());
        client.setActive(updatedClient.isActive());
        client.setAddress(updatedClient.getAddress());
        client.setUser(user);

        // Validar datos actualizados
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
        if (client.getUser() == null) {
            throw new IncompleteDataException("El usuario asociado es obligatorio.");
        }

        return new ClientResponseDTO(iClientRepository.save(client));
    }

    @Override
    public ClientResponseDTO deleteClient(int id) throws NotFoundException {
        Client client = iClientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Eliminar los préstamos asociados al cliente
        List<Loan> loans = iLoanRepository.findAllByUser_Id(id);
        for (Loan loan : loans) {
            iLoanRepository.delete(loan);
        }

        iClientRepository.deleteById(id);
        return new ClientResponseDTO(client);
    }

    @Override
    public List<ClientResponseDTO> searchClientsByQuery(int userId, String query) {
        if (query == null || query.trim().isEmpty()) {

            List<Client> all = iClientRepository.findAllByUser_Id(userId);
            List<ClientResponseDTO> allDTO = new ArrayList<>();

            for (Client client : all) {
                allDTO.add(new ClientResponseDTO(client));
            }

            return allDTO;
        }

        String queryLower = query.toLowerCase().trim();

        List<Client> all = this.iClientRepository.findAllByUser_Id(userId).stream()
                .filter(client -> String.valueOf(client.getId()).toLowerCase().contains(queryLower) ||
                                client.getFirstName().toLowerCase().contains(queryLower) ||
                                client.getSecondName().toLowerCase().contains(queryLower) ||
                                client.getFirstSurname().toLowerCase().contains(queryLower) ||
                                client.getSecondSurname().toLowerCase().contains(queryLower) ||
                                client.getEmail().toLowerCase().contains(queryLower) ||
                                client.getPhone().toLowerCase().contains(queryLower) ||
                                client.getAddress().toLowerCase().contains(queryLower)).toList();

        List<ClientResponseDTO> allDTO = new ArrayList<>();

        for (Client client : all) {
            allDTO.add(new ClientResponseDTO(client));
        }

        return allDTO;
    }
}