package com.loansytemapi.LoanSystem_Api.repository;

import com.loansytemapi.LoanSystem_Api.model.Income;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class IncomeRepository {

    private final Map<String, Income> dataBase = new HashMap<>();

    //Save
    public Income save (Income income) {
        dataBase.put(income.getIncomeID(), income);
        return income;
    }

    //Remove
    public void remove (String id) {
        dataBase.remove(id);
    }

    //Update
    public Income update(Income income) {
        if  (dataBase.containsKey(income.getIncomeID())) {
            dataBase.put(income.getIncomeID(), income);
            return income;
        }
        return null;
    }

    //Search
    public List<Income> findAll() {
        return new ArrayList<>(dataBase.values());
    }

    public Income findById(String id) {
        return dataBase.get(id);
    }

    public List<Income> getByFilter(String incomeType, Double minimumIncome, Double maximumIncome, Double incomeAmmount, String dateFilter) {
        return dataBase.values().stream()
                .filter(g -> incomeType == null || g.getIncomeType().equalsIgnoreCase(incomeType))
                .filter(g -> minimumIncome == null || g.getIncomeAmount() > minimumIncome)
                .filter(g -> maximumIncome == null || g.getIncomeAmount() < maximumIncome)
                .filter(g -> incomeAmmount == null || g.getIncomeAmount() == incomeAmmount)
                .filter(record -> dateFilter == null || cumpleFiltroFecha(record.getIncomeDate(), dateFilter))
                .collect(Collectors.toList());
    }

    private boolean cumpleFiltroFecha(LocalDate fecha, String filtroFecha) {
        LocalDate ahora = LocalDate.now();
        LocalDate fechaLimite = switch (filtroFecha.toLowerCase()) {
            case "1 semana" -> ahora.minusWeeks(1);
            case "1 mes" -> ahora.minusMonths(1);
            case "3 meses" -> ahora.minusMonths(3); // Corregido
            case "6 meses" -> ahora.minusMonths(6);
            case "1 año" -> ahora.minusYears(1);
            default -> null;
        };
        return fechaLimite == null || fecha.isAfter(fechaLimite);
    }
}
