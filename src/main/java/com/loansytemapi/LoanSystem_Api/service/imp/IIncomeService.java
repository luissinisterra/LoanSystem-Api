package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Income;
import java.util.List;

public interface IIncomeService {

    Income saveIncome(Income income) throws InvalidTextLengthException, InvalidAmmountException;

    List<Income> getAllIncomes();

    Income getIncomeById(Integer id) throws NotFoundException;

    void deleteIncome(Integer id) throws NotFoundException;

    Income updateIncome(Income income) throws NotFoundException;

   List<Income> getByUserId(Integer userId);

    List<Income> getByFilter(String incomeType, Integer min, Integer max, Integer exactAmount, String dateFilter);
}
