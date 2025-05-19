package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.dto.IncomeDTO;
import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Income;
import java.util.List;

public interface IIncomeService {

    Income saveIncome(IncomeDTO income) throws InvalidTextLengthException, InvalidAmmountException, NotFoundException;

    List<Income> getAllIncomes();

    Income getIncomeById(Integer id) throws NotFoundException;

    void deleteIncome(Integer id) throws NotFoundException;

    public Income updateIncome(Integer id, IncomeDTO income) throws NotFoundException, InvalidTextLengthException, InvalidAmmountException;

   List<Income> getByUserId(Integer userId);

    List<Income> getByFilter(String incomeType, Integer min, Integer max, Integer exactAmount, String dateFilter);
}
