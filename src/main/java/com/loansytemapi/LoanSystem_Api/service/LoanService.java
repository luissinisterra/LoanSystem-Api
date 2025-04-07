package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.model.Address;
import com.loansytemapi.LoanSystem_Api.model.Client;
import com.loansytemapi.LoanSystem_Api.model.Loan;
import com.loansytemapi.LoanSystem_Api.repository.LoanRepository;
import com.loansytemapi.LoanSystem_Api.repository.imp.ILoanRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService implements ILoanService {
    private final ILoanRepository iLoanRepository;

    @Autowired
    public LoanService(ILoanRepository iLoanRepository) {
        this.iLoanRepository = iLoanRepository;
        this.initSampleData();
    }

    // Método para inicializar datos de muestra
    public void initSampleData() {
        // Crear direcciones válidas para los clientes
        Address  address1 = new Address("Colombia", "Antioquia", "Medellín", "Calle 50 #34-21", "050010");
        Address address2 = new Address("Colombia", "Cundinamarca", "Bogotá", "Carrera 15 #72-45", "110111");

        // Crear clientes válidos
        Client client1 = new Client(
                "C001",
                "Juan",
                "Carlos",
                "Pérez",
                "Gómez",
                35,
                "juan.perez@example.com",
                "3001234567",
                address1
        );

        Client client2 = new Client(
                "C002",
                "María",
                "Isabel",
                "López",
                "Rodríguez",
                28,
                "maria.lopez@example.com",
                "3109876543",
                address2
        );

        // Crear préstamos asociados a los clientes
        Loan loan1 = new Loan(
                client1, // Asociar el cliente al préstamo
                15000.00,
                12312,
                123213,
                LocalDate.of(2025, 3, 15)
        );

        Loan loan2 = new Loan(
                client2, // Asociar el cliente al préstamo
                123213,
                123123,
                20000.00,
                LocalDate.of(2024, 6, 1)
        );

        // Guardar los préstamos en el repositorio (simulación)
        this.iLoanRepository.createLoan(loan1);
        this.iLoanRepository.createLoan(loan2);
    }

    @Override
    public List<Loan> getAllLoans() {
        return this.iLoanRepository.getAllLoans();
    }

    @Override
    public Loan getLoanById(String id) {
        return this.iLoanRepository.getLoanById(id);
    }

    @Override
    public Loan createLoan(Loan loan) {
        return this.iLoanRepository.createLoan(loan);
    }

    @Override
    public Loan deleteLoan(String id) {
        return this.iLoanRepository.deleteLoan(id);
    }

    @Override
    public Loan updateLoan(String id, Loan loan) {
        return this.iLoanRepository.updateLoan(id, loan);
    }

    @Override
    public List<Loan> searchLoansByQuery(String query) {
        return this.iLoanRepository.searchLoansByQuery(query);
    }
}
