package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.dto.LoanDTO;
import com.loansytemapi.LoanSystem_Api.dto.LoanResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Client;
import com.loansytemapi.LoanSystem_Api.model.Loan;
import com.loansytemapi.LoanSystem_Api.model.User;
import com.loansytemapi.LoanSystem_Api.repository.IClientRepository;
import com.loansytemapi.LoanSystem_Api.repository.ILoanRepository;
import com.loansytemapi.LoanSystem_Api.repository.IUserRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService implements ILoanService {

    private final ILoanRepository iLoanRepository;
    private final IClientRepository iClientRepository;
    private final IUserRepository iUserRepository;

    @Autowired
    public LoanService(ILoanRepository iLoanRepository, IClientRepository iClientRepository, IUserRepository iUserRepository) {
        this.iLoanRepository = iLoanRepository;
        this.iClientRepository = iClientRepository;
        this.iUserRepository = iUserRepository;
    }

    @Override
    public List<Loan> getAllLoans() {
        return iLoanRepository.findAll();
    }

    @Override
    public List<Loan> getAllLoansByClientId(int clientId) {
        return iLoanRepository.findAllByClient_Id(clientId);
    }

    @Override
    public List<Loan> getAllLoansByUserId(int userId) {
        return iLoanRepository.findAllByUser_Id(userId);
    }

    @Override
    public LoanResponseDTO getLoanById(int id) throws NotFoundException {
        return new LoanResponseDTO(iLoanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Préstamo no encontrado con ID: " + id)));
    }

    @Override
    public Loan createLoan(LoanDTO newLoan) throws IncompleteDataException, NotFoundException {

        Client client = this.iClientRepository.findById(newLoan.getClientId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        User user = this.iUserRepository.findById(newLoan.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Loan loan = new Loan();
        loan.setAmount(newLoan.getAmount());
        loan.setInterestRate(newLoan.getInterestRate());
        loan.setTerm(newLoan.getTerm());
        loan.setActive(newLoan.isActive());
        loan.setDate(newLoan.getDate());
        loan.setClient(client);
        loan.setUser(user);

        if (loan.getAmount() <= 0) {
            throw new IncompleteDataException("El monto debe ser mayor a cero.");
        }
        if (loan.getInterestRate() < 0) {
            throw new IncompleteDataException("La tasa de interés no puede ser negativa.");
        }
        if (loan.getTerm() <= 0) {
            throw new IncompleteDataException("El plazo debe ser mayor a cero.");
        }
        if (loan.getDate() == null || loan.getDate().isBefore(LocalDate.now())) {
            throw new IncompleteDataException("La fecha es obligatoria / no puede ser en el pasado.");
        }
        if (loan.getClient() == null) {
            throw new IncompleteDataException("El cliente es obligatorio.");
        }
        if (loan.getUser() == null) {
            throw new IncompleteDataException("El usuario es obligatorio.");
        }

        return iLoanRepository.save(loan);
    }

    @Override
    public LoanResponseDTO deleteLoan(int id) throws NotFoundException {
        LoanResponseDTO loan = getLoanById(id);
        iLoanRepository.deleteById(id);
        return loan;
    }

    @Override
    public Loan updateLoan(int id, LoanDTO updatedLoan) throws IncompleteDataException, NotFoundException {

        Client client = this.iClientRepository.findById(updatedLoan.getClientId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        User user = this.iUserRepository.findById(updatedLoan.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Loan loan = new Loan();
        loan.setId(id);
        loan.setAmount(updatedLoan.getAmount());
        loan.setInterestRate(updatedLoan.getInterestRate());
        loan.setTerm(updatedLoan.getTerm());
        loan.setActive(updatedLoan.isActive());
        loan.setDate(updatedLoan.getDate());
        loan.setClient(client);
        loan.setUser(user);

        if (loan.getAmount() <= 0) {
            throw new IncompleteDataException("El monto debe ser mayor a cero.");
        }
        if (loan.getInterestRate() < 0) {
            throw new IncompleteDataException("La tasa de interés no puede ser negativa.");
        }
        if (loan.getTerm() <= 0) {
            throw new IncompleteDataException("El plazo debe ser mayor a cero.");
        }
        if (loan.getDate() == null || loan.getDate().isBefore(LocalDate.now())) {
            throw new IncompleteDataException("La fecha es obligatoria / no puede ser en el pasado.");
        }
        if (loan.getClient() == null) {
            throw new IncompleteDataException("El cliente es obligatorio.");
        }
        if (loan.getUser() == null) {
            throw new IncompleteDataException("El usuario es obligatorio.");
        }

        return iLoanRepository.save(loan);
    }

    @Override
    public List<Loan> searchLoansByQuery(int userId, String query) {
        if (query == null || query.trim().isEmpty()) {
            return iLoanRepository.findAll();
        }

        return iLoanRepository.findAllByUser_Id(userId).stream().filter(loan ->
                                String.valueOf(loan.getId()).toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(loan.getAmount()).toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(loan.getInterestRate()).toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(loan.getTerm()).toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(loan.isActive() ? "Activo" : "Pagado").toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(loan.getDate()).toLowerCase().contains(query.toLowerCase())).toList();
    }
}