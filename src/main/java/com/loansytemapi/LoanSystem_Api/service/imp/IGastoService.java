package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.model.Gasto;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import java.util.List;

public interface IGastoService {

    Gasto save(Gasto gasto);
    void remove(String idGasto) throws NotFoundException;
    Gasto update(Gasto gasto) throws NotFoundException;
    List<Gasto> getAll();
    Gasto getByid(String idGasto);
    public List<Gasto> getByFilters(String tipoDeGasto, double gastoMinimo, double gastoMaximo, double montoGasto, String filtroFecha);
}
