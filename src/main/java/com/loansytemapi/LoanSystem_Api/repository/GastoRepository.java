package com.loansytemapi.LoanSystem_Api.repository;

import com.loansytemapi.LoanSystem_Api.model.Gasto;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class GastoRepository {

    private final Map<String, Gasto> baseDeDatos = new HashMap<>();


    //Ingresar gasto
    public Gasto save(Gasto gasto) {
        baseDeDatos.put(gasto.getIdGasto(), gasto);
        return gasto;
    }

    //Eliminar gasto
    public void delete(String idGasto) {
        baseDeDatos.remove(idGasto);
    }

    //Editar gasto
    public Gasto update(Gasto gasto) {
        if (baseDeDatos.containsKey(gasto.getIdGasto())) {
            baseDeDatos.put(gasto.getIdGasto(), gasto);
            return gasto;
        }
        return null;
    }

    //Metodos de listado
    public Gasto getById(String idGasto) {
        return baseDeDatos.get(idGasto);
    }

    public List<Gasto> getAll() {
        return new ArrayList<>(baseDeDatos.values());
    }

    public List<Gasto> getByFilter(String tipoDeGasto, double gastoMinimo, double gastoMaximo, double montoGasto, String filtroFecha) {
        return baseDeDatos.values().stream()
                .filter(g -> tipoDeGasto == null || g.getTipoDeGasto().contains(tipoDeGasto))
                .filter(g -> gastoMinimo == 0 || g.getValorGasto() > gastoMinimo)
                .filter(g -> gastoMaximo == 0 || g.getValorGasto() < gastoMaximo)
                .filter(g -> montoGasto == 0 || g.getValorGasto() == montoGasto)
                .filter(record -> filtroFecha == null || cumpleFiltroFecha(record.getFechaGasto(), filtroFecha)) // <- Filtra por últimos registros
                .collect(Collectors.toList());
    }
    private boolean cumpleFiltroFecha(LocalDateTime fecha, String filtroFecha) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaLimite = switch (filtroFecha.toLowerCase()) {
            case "1 semana" ->
                    ahora.minusWeeks(1);
            case "1 mes" ->
                    ahora.minusMonths(1);
            case "3 meses" ->
                    ahora.minusWeeks(12);
            case "6 meses" ->
                    ahora.minusMonths(6);
            case "1 año" ->
                    ahora.minusYears(1);
            default ->
                    null;
        };
        return fechaLimite == null || fecha.isAfter(fechaLimite);
    }
}

