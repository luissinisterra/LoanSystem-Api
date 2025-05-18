package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.dto.LoanDTO;
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

import java.util.List;
import java.util.stream.Collectors;

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
    public List<Loan> getAllLoansByUserId(int userId) {
        return iLoanRepository.findAllByUser_Id(userId);
    }

    @Override
    public Loan getLoanById(int id) throws NotFoundException {
        return iLoanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Préstamo no encontrado con ID: " + id));
    }

    @Override
    public Loan createLoan(LoanDTO newLoan) throws IncompleteDataException, NotFoundException {

        // Asegúrate de que el cliente existe
        Client client = this.iClientRepository.findById(newLoan.getClientId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        // Igual con el usuario
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
        if (loan.getDate() == null) {
            throw new IncompleteDataException("La fecha es obligatoria.");
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
    public Loan deleteLoan(int id) throws NotFoundException {
        Loan loan = getLoanById(id);
        iLoanRepository.deleteById(id);
        return loan;
    }

    @Override
    public Loan updateLoan(int id, LoanDTO updatedLoan) throws IncompleteDataException, NotFoundException {
        Loan existingLoan = getLoanById(id);

        // Asegúrate de que el cliente existe
        Client client = this.iClientRepository.findById(updatedLoan.getClientId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        // Igual con el usuario
        User user = this.iUserRepository.findById(updatedLoan.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        existingLoan.setAmount(updatedLoan.getAmount());
        existingLoan.setInterestRate(updatedLoan.getInterestRate());
        existingLoan.setTerm(updatedLoan.getTerm());
        existingLoan.setActive(updatedLoan.isActive());
        existingLoan.setDate(updatedLoan.getDate());
        existingLoan.setClient(client);
        existingLoan.setUser(user);

        if (existingLoan.getAmount() <= 0) {
            throw new IncompleteDataException("El monto debe ser mayor a cero.");
        }
        if (existingLoan.getInterestRate() < 0) {
            throw new IncompleteDataException("La tasa de interés no puede ser negativa.");
        }
        if (existingLoan.getTerm() <= 0) {
            throw new IncompleteDataException("El plazo debe ser mayor a cero.");
        }
        if (existingLoan.getDate() == null) {
            throw new IncompleteDataException("La fecha es obligatoria.");
        }
        if (existingLoan.getClient() == null) {
            throw new IncompleteDataException("El cliente es obligatorio.");
        }
        if (existingLoan.getUser() == null) {
            throw new IncompleteDataException("El usuario es obligatorio.");
        }

        return iLoanRepository.save(existingLoan);
    }

    @Override
    public List<Loan> searchLoansByQuery(int userId, String query) {
        if (query == null || query.trim().isEmpty()) {
            return iLoanRepository.findAll();
        }
        //return iLoanRepository.findLoansByCriteria(query);

        return iLoanRepository.findAllByUser_Id(userId).stream().filter(loan ->
                                String.valueOf(loan.getId()).toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(loan.getAmount()).toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(loan.getInterestRate()).toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(loan.getTerm()).toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(loan.isActive() ? "Activo" : "Pagado").toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(loan.getDate()).toLowerCase().contains(query.toLowerCase())).toList();
    }
}