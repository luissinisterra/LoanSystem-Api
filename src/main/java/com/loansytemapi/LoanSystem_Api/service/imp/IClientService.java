package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.dto.ClientDTO;
import com.loansytemapi.LoanSystem_Api.dto.ClientResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Client;
import java.util.List;

public interface IClientService {
    List<ClientResponseDTO> getAllClients();
    List<ClientResponseDTO> getAllClientsByUserId(int userId) throws NotFoundException;
    ClientResponseDTO getClientById(int id) throws NotFoundException;
    ClientResponseDTO createClient(ClientDTO client) throws IncompleteDataException, NotFoundException;
    ClientResponseDTO updateClient(int id, ClientDTO client) throws IncompleteDataException, NotFoundException;
    ClientResponseDTO deleteClient(int id) throws NotFoundException;
    List<ClientResponseDTO> searchClientsByQuery(int userId, String query);
}
