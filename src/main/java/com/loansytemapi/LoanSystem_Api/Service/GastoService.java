package com.loansytemapi.LoanSystem_Api.Service;

import com.loansytemapi.LoanSystem_Api.Model.Gasto;
import com.loansytemapi.LoanSystem_Api.Repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastoService {
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
    //Save
    public Gasto save(Gasto gasto) {
        return gastoRepository.save(gasto);
    }

    //Remove
    public void delete(String idGasto) {
        Gasto gasto = gastoRepository.getById(idGasto);
        if  (gasto != null) {
            gastoRepository.delete(idGasto);
        }
    }

    //Update
    public Gasto update (Gasto gasto) {
        Gasto g = gastoRepository.update(gasto);
        if (g != null) {
            return g;
        }
        return null;
    }

    //Listar
    public List<Gasto> getAll() {
       return gastoRepository.getAll();
    }

    public Gasto getByid(String idGasto) {
        Gasto gasto = gastoRepository.getById(idGasto);
        if  (gasto != null) {
            return gasto;
        }
        return null;
    }

    public List<Gasto> getByFilters(String tipoDeGasto, double gastoMinimo, double gastoMaximo, double montoGasto, String filtroFecha) {
        return gastoRepository.getByFilter(tipoDeGasto, gastoMinimo, gastoMaximo, montoGasto, filtroFecha);
    }
}
