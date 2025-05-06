package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.exception.*;
import com.loansytemapi.LoanSystem_Api.model.Overhead;
import com.loansytemapi.LoanSystem_Api.repository.OverheadRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.IGastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OverheadService implements IGastoService {

    private final OverheadRepository overheadRepository;

    @Autowired
    public OverheadService(OverheadRepository overheadRepository) {
        this.overheadRepository = overheadRepository;
    }
    @Override
    public Overhead save(Overhead overhead) throws InvalidTextLengthException, InvalidAmmountException, IncompleteDataException {
        if (overhead.getOverhead_description() == null || overhead.getOverhead_description().isEmpty() ||
            overhead.getOverhead_type() == null || overhead.getOverhead_type().isEmpty() ||
            overhead.getAmmount() == null || overhead.getAmmount() <= 0 ||
            overhead.getUser_id() == null) {
            throw new IncompleteDataException("ERROR: Datos incompletos");
        }

        if (overhead.getOverhead_description().length() > 50) {
            throw new InvalidTextLengthException("ERROR: La descripción no debe superar los 50 caracteres");
        }

        return overheadRepository.save(overhead);
    }

    @Override
    public void remove(String id) throws NotFoundException {
        int gastoId = Integer.parseInt(id);
        if (!overheadRepository.existsById(gastoId)) {
            throw new NotFoundException("ERROR: Gasto no encontrado");
        }
        overheadRepository.deleteById(gastoId);
    }

    @Override
    public Overhead update(Overhead gasto) throws NotFoundException {
        Integer id = gasto.getId();
        Optional<Overhead> existing = overheadRepository.findById(id);
        if (existing.isEmpty()) {
            throw new NotFoundException("ERROR: Gasto no encontrado");
        }
        gasto.setOverhead_date(existing.get().getOverhead_date()); // Mantiene la fecha original
        return overheadRepository.save(gasto);
    }

    @Override
    public List<Overhead> getAll() {
        return overheadRepository.findAll();
    }

    @Override
    public Overhead getByid(String id) throws NotFoundException {
        int gastoId = Integer.parseInt(id);
        return overheadRepository.findById(gastoId)
                .orElseThrow(() -> new NotFoundException("ERROR: Gasto no encontrado"));
    }

    @Override
    public List<Overhead> getByFilters(String tipoDeGasto, double gastoMinimo, double gastoMaximo, double montoGasto, String filtroFecha) {
        return overheadRepository.findAll().stream()
                .filter(g -> tipoDeGasto == null || g.getOverhead_type().contains(tipoDeGasto))
                .filter(g -> gastoMinimo == 0 || g.getAmmount() > gastoMinimo)
                .filter(g -> gastoMaximo == 0 || g.getAmmount() < gastoMaximo)
                .filter(g -> montoGasto == 0 || g.getAmmount() == montoGasto)
                .filter(record -> filtroFecha == null || cumpleFiltroFecha(record.getOverhead_date(), filtroFecha))
                .collect(Collectors.toList());
    }

    private boolean cumpleFiltroFecha(LocalDate fecha, String filtroFecha) {
        LocalDate ahora = LocalDate.now();
        LocalDate fechaLimite = switch (filtroFecha.toLowerCase()) {
            case "1 semana" -> ahora.minusWeeks(1);
            case "1 mes" -> ahora.minusMonths(1);
            case "3 meses" -> ahora.minusWeeks(12);
            case "6 meses" -> ahora.minusMonths(6);
            case "1 año" -> ahora.minusYears(1);
            default -> null;
        };
        return fechaLimite == null || fecha.isAfter(fechaLimite);
    }
}
