package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.dto.IncomeCreateDTO;
import com.loansytemapi.LoanSystem_Api.dto.IncomeResponseDTO;
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
import java.util.LinkedList;
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
    public IncomeResponseDTO saveIncome(IncomeCreateDTO income) throws InvalidTextLengthException, InvalidAmmountException, NotFoundException {
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
        return new IncomeResponseDTO(incomeRepository.save(newIncome));
    }

    @Override
    public List<IncomeResponseDTO> getAllIncomes() {
        List<Income> all = incomeRepository.findAll();
        List<IncomeResponseDTO> response = new LinkedList<>();
        for (Income i : all) {
            response.add(new IncomeResponseDTO(i));
        }
        return response;
    }

    @Override
    public IncomeResponseDTO getIncomeById(Integer id) throws NotFoundException {
        Optional<Income> income = incomeRepository.findById(id);
        if (income.isEmpty()){
            throw new NotFoundException("Income not found");
        }
        Income i = income.get();
        return new IncomeResponseDTO(i);
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
    public IncomeResponseDTO updateIncome(Integer id, IncomeCreateDTO income) throws NotFoundException, InvalidTextLengthException, InvalidAmmountException {
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
        Income updatedIncome = incomeRepository.save(i.get());
        return new IncomeResponseDTO(updatedIncome);
    }

    @Override
    public List<IncomeResponseDTO> getByUserId(Integer userId) {
        List<Income> all = incomeRepository.findAllByUser_id(userId);
        List<IncomeResponseDTO> response = new LinkedList<>();
        for (Income i : all) {
            response.add(new IncomeResponseDTO(i));
        }
        return response;
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
