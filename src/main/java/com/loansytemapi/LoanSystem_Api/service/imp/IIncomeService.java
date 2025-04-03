package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Income;

import java.util.List;

public interface IIncomeService {
    Income save(Income income) throws InvalidTextLengthException, InvalidAmmountException, IncompleteDataException;
    void remove(String id) throws NotFoundException;
    Income update(Income income) throws NotFoundException;
    List<Income> getAll();
    Income getByid(String id);
    public List<Income> getByFilters(String incomeType, Double minimumIncome, Double maximumIncome, Double incomeAmmount, String dateFilter);
}
