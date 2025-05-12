package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Income;
import com.loansytemapi.LoanSystem_Api.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loansytemapi.LoanSystem_Api.service.imp.IIncomeService;
import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeService implements IIncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public Income saveIncome(Income income) throws InvalidTextLengthException, InvalidAmmountException {
        if (income.getIncome_description().length() > 50){
            throw new InvalidTextLengthException("The income description must not exceed 50 characters.");
        }
        if (income.getAmmount() <= 0){
            throw new InvalidAmmountException("The amount must be greater than zero.");
        }
        return incomeRepository.save(income);
    }

    @Override
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    @Override
    public Income getIncomeById(Integer id) throws NotFoundException {
        Income income = incomeRepository.findById(id).get();
        if (income == null){
            throw new NotFoundException ("Income not found");
        }
        return income;
    }

    @Override
    public void deleteIncome(Integer id) throws NotFoundException {
        Income income = incomeRepository.findById(id).get();
        if (income == null){
            throw new NotFoundException("Income not found");
        }
        incomeRepository.delete(income);
    }

    @Override
    public Income updateIncome(Income income) throws NotFoundException {
        Income i = incomeRepository.findById(income.getId()).get();
        if (income == null){
            throw new NotFoundException("Income not found");
        }
        income.setId(i.getId());
        income.setIncome_date(i.getIncome_date());
        return incomeRepository.save(income);
    }

    @Override
    public List<Income> getByUserId(Integer userId) throws NotFoundException {
        List<Income> incomes = incomeRepository.findAll();
        if (incomes == null){
            throw new NotFoundException("Incomes not found");
        }
        return incomes;
    }

    @Override
    public List<Income> getByFilter(String incomeType, Integer min, Integer max, Integer exactAmount, String dateFilter) {
        List<Income> all = incomeRepository.findAll();
        return all.stream()
                .filter(i -> incomeType == null || i.getIncome_type().equalsIgnoreCase(incomeType))
                .filter(i -> min == null || i.getAmmount() > min)
                .filter(i -> max == null || i.getAmmount() < max)
                .filter(i -> exactAmount == null || i.getAmmount().equals(exactAmount))
                .filter(i -> cumpleFiltroFecha(i.getIncome_date(), dateFilter))
                .toList();
    }

    // Lógica para filtrar por fecha
    private boolean cumpleFiltroFecha(LocalDate fecha, String filtroFecha) {
        LocalDate ahora = LocalDate.now();
        LocalDate fechaLimite = switch (filtroFecha == null ? "" : filtroFecha.toLowerCase()) {
            case "1 semana" -> ahora.minusWeeks(1);
            case "1 mes" -> ahora.minusMonths(1);
            case "3 meses" -> ahora.minusMonths(3);
            case "6 meses" -> ahora.minusMonths(6);
            case "1 año" -> ahora.minusYears(1);
            default -> null;
        };
        return fechaLimite == null || fecha.isAfter(fechaLimite);
    }


}
