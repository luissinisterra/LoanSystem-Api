package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Income;
import com.loansytemapi.LoanSystem_Api.service.imp.IIncomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
@Tag(name = "Ingresos", description = "API para la gestión de ingresos")
public class IncomeController {

    private final IIncomeService incomeService;

    @Autowired
    public IncomeController(IIncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @Operation(summary = "Obtener todos los ingresos", description = "Retorna una lista de todos los ingresos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ingresos obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<Income>> getAll() {
        return ResponseEntity.ok(incomeService.getAll());
    }

    @Operation(summary = "Obtener un ingreso por ID", description = "Busca un ingreso específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingreso encontrado"),
            @ApiResponse(responseCode = "404", description = "Ingreso no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Income> getById(@PathVariable @Parameter(description = "ID del ingreso que queremos encontrar") String id) throws NotFoundException {
        return ResponseEntity.ok(incomeService.getByid(id));
    }

    @Operation(summary = "Crear un nuevo ingreso", description = "Guarda un nuevo ingreso en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingreso creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para la creación")
    })
    @PostMapping
    public ResponseEntity<Income> save(@RequestBody Income income) throws IncompleteDataException, InvalidTextLengthException, InvalidAmmountException {
        return ResponseEntity.status(201).body(incomeService.save(income));
    }

    @Operation(summary = "Actualizar un ingreso", description = "Modifica un ingreso existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingreso actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Ingreso no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Income> update(@PathVariable @Parameter(description = "ID del ingreso que queremos editar") String id, @RequestBody Income income) throws NotFoundException {
        income.setIncomeID(id);
        Income i = incomeService.update(income);
        return ResponseEntity.ok(i);
    }

    @Operation(summary = "Eliminar un ingreso", description = "Elimina un ingreso según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ingreso eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Ingreso no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable @Parameter(description = "ID del ingreso que queremos eliminar") String id) throws NotFoundException {
        incomeService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar ingresos", description = "Obtiene una lista de ingresos según los criterios de búsqueda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ingresos filtrada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron ingresos con los filtros proporcionados")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<Income>> getByFilters(
            @RequestParam(required = false) @Parameter(description = "Tipo de ingreso por el que queremos filtrar (opcional)") String incomeType,
            @RequestParam(required = false) @Parameter(description = "Valor mínimo de ingreso por el que queremos filtrar (opcional)") Double minimumIncome,
            @RequestParam(required = false) @Parameter(description = "Valor máximo de ingreso por el que queremos filtrar (opcional)") Double maximumIncome,
            @RequestParam(required = false) @Parameter(description = "Valor específico por el que queremos filtrar (opcional)") Double incomeAmmount,
            @RequestParam(required = false) @Parameter(description = "Filtro de fechas (opcional)") String dateFilter
    ) throws NotFoundException {
        return ResponseEntity.ok(incomeService.getByFilters(incomeType, minimumIncome, maximumIncome, incomeAmmount, dateFilter));
    }
}

