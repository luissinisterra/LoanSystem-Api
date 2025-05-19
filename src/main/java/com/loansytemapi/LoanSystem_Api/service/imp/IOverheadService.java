package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.dto.CreateOverheadDTO;
import com.loansytemapi.LoanSystem_Api.dto.OverheadResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.model.Overhead;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import java.util.List;

public interface IOverheadService {

    OverheadResponseDTO save(CreateOverheadDTO overhead) throws InvalidTextLengthException, InvalidAmmountException, NotFoundException;
    void remove(Integer idGasto) throws NotFoundException;
    OverheadResponseDTO update(Integer id, CreateOverheadDTO gasto) throws NotFoundException;
    List<OverheadResponseDTO> getAll();
    List<OverheadResponseDTO> getByUserId(Integer idUser) throws NotFoundException;
    OverheadResponseDTO getByid(Integer idGasto) throws NotFoundException;
    List<Overhead> getByFilters(String tipoDeGasto, double gastoMinimo, double gastoMaximo, double montoGasto, String filtroFecha);
}
