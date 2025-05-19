package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.dto.IncomeDTO;
import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Income;
import com.loansytemapi.LoanSystem_Api.model.User;
import com.loansytemapi.LoanSystem_Api.repository.IUserRepository;
import com.loansytemapi.LoanSystem_Api.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loansytemapi.LoanSystem_Api.service.imp.IIncomeService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService implements IIncomeService {

    @Autowired
    private IncomeRepository incomeRepository;
    private IUserRepository userRepository;

    public IncomeService(IncomeRepository incomeRepository, IUserRepository userRepository) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Income saveIncome(IncomeDTO income) throws InvalidTextLengthException, InvalidAmmountException, NotFoundException {
        Optional<User> user = userRepository.findById(income.getUserId());
        if (user.isEmpty()){
            throw new NotFoundException("User not found");
        }
        Income newIncome = new Income();
        newIncome.setAmmount(income.getAmmount());
        newIncome.setIncome_description(income.getIncomeDescription());
        newIncome.setIncome_type(income.getIncomeType());
        newIncome.setUser(user.get());
        if (newIncome.getIncome_description().length() > 50) {
            throw new InvalidTextLengthException("The income description must not exceed 50 characters.");
        }
        if (income.getAmmount() <= 0){
            throw new InvalidAmmountException("The amount must be greater than zero.");
        }
        return incomeRepository.save(newIncome);
    }

    @Override
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    @Override
    public Income getIncomeById(Integer id) throws NotFoundException {
        Optional<Income> income = incomeRepository.findById(id);
        if (income.isEmpty()){
            throw new NotFoundException("Income not found");
        }
        return income.get();
    }

    @Override
    public void deleteIncome(Integer id) throws NotFoundException {
        Optional<Income> income = incomeRepository.findById(id);
        if (income.isEmpty()){
            throw new NotFoundException("Income not found");
        }
        incomeRepository.deleteById(id);
    }

    @Override
    public Income updateIncome(Integer id, IncomeDTO income) throws NotFoundException, InvalidTextLengthException, InvalidAmmountException {
        Optional<Income> i = incomeRepository.findById(id);
        if (i.isEmpty()){
            throw new NotFoundException("Income not found");
        }
        i.get().setAmmount(income.getAmmount());
        i.get().setIncome_description(income.getIncomeDescription());
        i.get().setIncome_type(income.getIncomeType());
        if (i.get().getIncome_description().length() > 50) {
            throw new InvalidTextLengthException("The income description must not exceed 50 characters.");
        }
        if (income.getAmmount() <= 0){
            throw new InvalidAmmountException("The amount must be greater than zero.");
        }
        return incomeRepository.save(i.get());
    }

    @Override
    public List<Income> getByUserId(Integer userId) {
        return incomeRepository.findAllByUser_id(userId);
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
