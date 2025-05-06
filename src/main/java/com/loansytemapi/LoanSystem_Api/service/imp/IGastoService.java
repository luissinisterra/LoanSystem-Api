package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.model.Overhead;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import java.util.List;

public interface IGastoService {

    Overhead save(Overhead gasto)throws InvalidTextLengthException, InvalidAmmountException, IncompleteDataException;
    void remove(String idGasto) throws NotFoundException;
    Overhead update(Overhead gasto) throws NotFoundException;
    List<Overhead> getAll();
    Overhead getByid(String idGasto) throws NotFoundException;
    public List<Overhead> getByFilters(String tipoDeGasto, double gastoMinimo, double gastoMaximo, double montoGasto, String filtroFecha);
}
