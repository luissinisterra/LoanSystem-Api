package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.dto.IncomeCreateDTO;
import com.loansytemapi.LoanSystem_Api.dto.IncomeResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Income;
import java.util.List;

public interface IIncomeService {

    IncomeResponseDTO saveIncome(IncomeCreateDTO income) throws InvalidTextLengthException, InvalidAmmountException, NotFoundException;

    List<IncomeResponseDTO> getAllIncomes();

    IncomeResponseDTO getIncomeById(Integer id) throws NotFoundException;

    void deleteIncome(Integer id) throws NotFoundException;

    public IncomeResponseDTO updateIncome(Integer id, IncomeCreateDTO income) throws NotFoundException, InvalidTextLengthException, InvalidAmmountException;

   List<IncomeResponseDTO> getByUserId(Integer userId);

    List<Income> getByFilter(String incomeType, Integer min, Integer max, Integer exactAmount, String dateFilter);
}
