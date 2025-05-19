package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.dto.OverheadDTO;
import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.model.Overhead;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import java.util.List;

public interface IOverheadService {

    Overhead save(OverheadDTO overhead) throws InvalidTextLengthException, InvalidAmmountException, NotFoundException;
    void remove(Integer idGasto) throws NotFoundException;
    Overhead update(Integer id, OverheadDTO gasto) throws NotFoundException;
    List<Overhead> getAll();
    List<Overhead> getByUserId(Integer idUser) throws NotFoundException;
    Overhead getByid(Integer idGasto) throws NotFoundException;
    List<Overhead> getByFilters(String tipoDeGasto, double gastoMinimo, double gastoMaximo, double montoGasto, String filtroFecha);
}
