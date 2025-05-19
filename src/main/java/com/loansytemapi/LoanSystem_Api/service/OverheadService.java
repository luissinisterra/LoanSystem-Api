package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.dto.CreateOverheadDTO;
import com.loansytemapi.LoanSystem_Api.dto.OverheadResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.*;
import com.loansytemapi.LoanSystem_Api.model.Overhead;
import com.loansytemapi.LoanSystem_Api.model.User;
import com.loansytemapi.LoanSystem_Api.repository.IUserRepository;
import com.loansytemapi.LoanSystem_Api.repository.OverheadRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.IOverheadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OverheadService implements IOverheadService {

    private final OverheadRepository overheadRepository;
    private final IUserRepository userRepository;

    @Autowired
    public OverheadService(OverheadRepository overheadRepository, IUserRepository userRepository) {
        this.overheadRepository = overheadRepository;
        this.userRepository = userRepository;
    }
    @Override
    public OverheadResponseDTO save(CreateOverheadDTO overhead) throws InvalidTextLengthException, InvalidAmmountException, NotFoundException {
        Optional<User> user = userRepository.findById(overhead.getUserId());
        if (user.isEmpty()){
            throw new NotFoundException("The user with id " + overhead.getUserId() + " does not exist.");
        }
        Overhead newOverhead = getOverhead(overhead, user);
        return new OverheadResponseDTO(overheadRepository.save(newOverhead));
    }


    private Overhead getOverhead(CreateOverheadDTO overhead, Optional<User> user) throws InvalidTextLengthException, InvalidAmmountException {
        Overhead newOverhead = new Overhead();
        newOverhead.setOverhead_type(overhead.getOverheadType());
        newOverhead.setOverhead_description(overhead.getOverheadDescription());
        newOverhead.setAmmount(overhead.getAmmount());
        newOverhead.setUser(user.get());
        newOverhead.setOverhead_date(overhead.getOverheadDate());
        if (newOverhead.getOverhead_description().length() > 50) {
            throw new InvalidTextLengthException("The income description must not exceed 50 characters.");
        }
        if (newOverhead.getAmmount() <= 0){
            throw new InvalidAmmountException("The income amount must be greater than zero.");
        }
        return newOverhead;
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
    public OverheadResponseDTO update(Integer id, CreateOverheadDTO overhead) throws NotFoundException {
        Optional<Overhead> overheadToUpdate = overheadRepository.findById(id);
        if (overheadToUpdate.isEmpty()) {
            throw new NotFoundException("The overhead with id " + id + " does not exist.");
        }
        Overhead updatedOverhead = overheadToUpdate.get();
        updatedOverhead.setOverhead_type(overhead.getOverheadType());
        updatedOverhead.setOverhead_description(overhead.getOverheadDescription());
        updatedOverhead.setAmmount(overhead.getAmmount());
        return new OverheadResponseDTO(overheadRepository.save(updatedOverhead));
    }

    @Override
    public List<OverheadResponseDTO> getAll() {
        List<Overhead> all = overheadRepository.findAll();
        List<OverheadResponseDTO> response = new LinkedList<>();
        for (Overhead overhead : all) {
            response.add(new OverheadResponseDTO(overhead));
        }
        return response;
    }

    @Override
    public OverheadResponseDTO getByid(Integer id) throws NotFoundException {
        Optional<Overhead> overhead = overheadRepository.findById(id);
        if (overhead.isEmpty()) {
            throw new NotFoundException("The overhead with id " + id + " does not exist.");
        }
        return new OverheadResponseDTO(overhead.get());
    }

    @Override
    public List<OverheadResponseDTO> getByUserId(Integer userId) {
        List<Overhead> all = overheadRepository.findAllByUser_id(userId);
        List<OverheadResponseDTO> response = new LinkedList<>();
        for (Overhead overhead : all) {
            response.add(new OverheadResponseDTO(overhead));
        }
        return response;
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
