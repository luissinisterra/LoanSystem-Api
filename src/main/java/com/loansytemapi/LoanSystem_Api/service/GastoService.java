package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.model.Gasto;
import com.loansytemapi.LoanSystem_Api.repository.GastoRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.IGastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loansytemapi.LoanSystem_Api.exception.*;
import java.util.List;

@Service
public class GastoService implements IGastoService {
    private final GastoRepository gastoRepository;

    @Autowired
    public GastoService(GastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;
        initData();
    }

    //Datos quemados
    private void initData (){
        gastoRepository.save(new Gasto("Gasto", "Gasto gasolina", 50.000));
        gastoRepository.save(new Gasto("Gasto", "Gasto cadena", 100.000));
        gastoRepository.save(new Gasto("Salida", "Almuerzo", 20.000));
    }
    @Override
    public Gasto save(Gasto gasto) throws InvalidTextLengthException, InvalidAmmountException, IncompleteDataException {
        if (gasto.getDescripcionGasto() == null || gasto.getDescripcionGasto().isEmpty() || gasto.getTipoDeGasto() == null || gasto.getTipoDeGasto().isEmpty()
            || gasto.getValorGasto() == 0) {
            throw new IncompleteDataException("ERROR: Falta información para crear el gasto correctamente");
        }
        if (gasto.getDescripcionGasto().length() > 50) {
            throw new InvalidTextLengthException("ERROR: La descripción del gasto no debe superar los 50 caracteres");
        }
        if (gasto.getValorGasto() < 0) {
            throw new InvalidAmmountException("ERROR: El valor del gasto no puede ser negativo o igual");
        }
        return gastoRepository.save(gasto);
    }


    @Override
    //Remove
    public void remove(String idGasto) throws NotFoundException {
        Gasto gasto = gastoRepository.getById(idGasto);
        if  (gasto == null) {
            throw new NotFoundException("ERROR: No se ha encontrado el gasto que quiere eliminar");
        }
        gastoRepository.remove(idGasto);
    }

    @Override
    //Update
    public Gasto update (Gasto gasto) throws NotFoundException {
        Gasto g = gastoRepository.getById(gasto.getIdGasto());
        if (g == null) {
            throw new  NotFoundException("ERROR: No se ha encontrado el gasto");
        }
        gasto.setFechaGasto(g.getFechaGasto());
        return gastoRepository.update(gasto);
    }

    @Override
    //Listar
    public List<Gasto> getAll() {
       return gastoRepository.getAll();
    }

    @Override
    public Gasto getByid(String idGasto) throws NotFoundException {
        Gasto gasto = gastoRepository.getById(idGasto);
        if  (gasto == null) {
            throw new NotFoundException("ERROR: No se ha encontrado el gasto");
        }
        return gasto;
    }

    @Override
    public List<Gasto> getByFilters(String tipoDeGasto, double gastoMinimo, double gastoMaximo, double montoGasto, String filtroFecha) {
        return gastoRepository.getByFilter(tipoDeGasto, gastoMinimo, gastoMaximo, montoGasto, filtroFecha);
    }
}
