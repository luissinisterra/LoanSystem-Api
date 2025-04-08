package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Income;
import com.loansytemapi.LoanSystem_Api.repository.GastoRepository;
import com.loansytemapi.LoanSystem_Api.repository.IncomeRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.IIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService implements IIncomeService {

    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository) throws IncompleteDataException, InvalidTextLengthException, InvalidAmmountException {
        this.incomeRepository = incomeRepository;
        initSampleData();
    }


    private void initSampleData() throws IncompleteDataException, InvalidTextLengthException, InvalidAmmountException {
        save (new Income("Pago", "Mary pago la cuota semanal", 500));
        save (new Income("Pago", "Juan pago la cuota mensual", 50000));
        save (new Income("Ingreso", "Ingreso del 20% del pago de Mary", 80000));
    }

    @Override
    public Income save(Income income) throws InvalidTextLengthException, InvalidAmmountException, IncompleteDataException{
        if (income.getIncomeDescription() == null || income.getIncomeDescription().isEmpty() || income.getIncomeType() == null
                || income.getIncomeType().isEmpty() || income.getIncomeAmount() == 0) {
            throw new IncompleteDataException("ERROR: Falta información valida para crear el ingreso correctamente");
        }
        if (income.getIncomeDescription().length() > 50) {
            throw new InvalidTextLengthException("ERROR: La descripción del ingreso no debe superar los 50 caracteres");
        }
        if (income.getIncomeAmount() < 0) {
            throw new InvalidAmmountException("ERROR: El valor del ingreso no puede ser negativo o igual");
        }
        return  incomeRepository.save(income);
    }

    @Override
    public void remove(String id) throws NotFoundException {
        Income i = incomeRepository.findById(id);
        if (i == null) {
            throw new NotFoundException("ERROR: No se ha encontrado el ingreso que quiere eliminar");
        }
        incomeRepository.remove(id);
    }

    @Override
    public Income update(Income income) throws NotFoundException {
        Income i = incomeRepository.findById(income.getIncomeID());
        if (i == null) {
            throw new NotFoundException("ERROR: No se ha encontrado el ingreso que desea actualizar");
        }
        income.setIncomeDate(i.getIncomeDate());
        incomeRepository.update(income);
        return income;
    }

    @Override
    public List<Income> getAll() {
        return incomeRepository.findAll();
    }

    @Override
    public Income getByid(String id) throws NotFoundException {
        Income i = incomeRepository.findById(id);
        if (i == null) {
            throw new NotFoundException("ERROR: No se ha encontrado el ingreso");
        }
        return i;
    }

    @Override
    public List<Income> getByFilters(String incomeType, Double minimumIncome, Double maximumIncome, Double incomeAmmount, String dateFilter) throws NotFoundException {
        List<Income> result = incomeRepository.getByFilter(incomeType, minimumIncome, maximumIncome, incomeAmmount, dateFilter);
        if (result.isEmpty()) {
            throw new NotFoundException("ERROR: No se han encontrado ingresos por los datos que suministro");
        }
        return result;
    }
}
