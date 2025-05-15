package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.exception.*;
import com.loansytemapi.LoanSystem_Api.model.Overhead;
import com.loansytemapi.LoanSystem_Api.repository.OverheadRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.IOverheadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OverheadService implements IOverheadService {

    private final OverheadRepository overheadRepository;

    @Autowired
    public OverheadService(OverheadRepository overheadRepository) {
        this.overheadRepository = overheadRepository;
    }
    @Override
    public Overhead save(Overhead overhead) throws InvalidTextLengthException, InvalidAmmountException{
        if (overhead.getOverhead_description().length() > 50) {
            throw new InvalidTextLengthException("The income description must not exceed 50 characters.");
        }
        if (overhead.getAmmount() <= 0){
            throw new InvalidAmmountException("The income amount must be greater than zero.");
        }
        return overheadRepository.save(overhead);
    }

    @Override
    public void remove(Integer id) throws NotFoundException {
        Optional<Overhead> overhead = overheadRepository.findById(id);
        if (overhead.isEmpty()) {
            throw new NotFoundException("The overhead with id " + id + " does not exist.");
        }
        overheadRepository.deleteById(id);
    }

    @Override
    public Overhead update(Overhead gasto) throws NotFoundException {
        Optional<Overhead> overhead = overheadRepository.findById(gasto.getId());
        if (overhead.isEmpty()) {
            throw new NotFoundException("The overhead with id " + gasto.getId() + " does not exist.");
        }
        gasto.setOverhead_date(overhead.get().getOverhead_date());
        gasto.setId(overhead.get().getId());
        return overheadRepository.save(gasto);
    }

    @Override
    public List<Overhead> getAll() {
        return overheadRepository.findAll();
    }

    @Override
    public Overhead getByid(Integer id) throws NotFoundException {
        Optional<Overhead> overhead = overheadRepository.findById(id);
        if (overhead.isEmpty()) {
            throw new NotFoundException("The overhead with id " + id + " does not exist.");
        }
        return overhead.get();
    }

    @Override
    public List<Overhead> getByUserId(Integer userId) {
        return overheadRepository.findAllByUser_id(userId);
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
