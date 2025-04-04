package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Gasto;
import com.loansytemapi.LoanSystem_Api.service.GastoService;
import com.loansytemapi.LoanSystem_Api.service.imp.IGastoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gastos")
@Tag(name = "Gastos", description = "API para la gestión de gastos")
public class GastoController {

    private final IGastoService gastoService;

    @Autowired
    public GastoController(IGastoService gastoService) {
        this.gastoService = gastoService;
    }

    //POST
    @PostMapping
    @Operation (summary = "Crear un gasto", description = "Crea un nuevo gasto que haga el usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gasto creado"),
            @ApiResponse(responseCode = "400", description = "Error en el gasto")
    })
    public ResponseEntity<Gasto> saveGasto(@RequestBody @Parameter(description = "Body con el gasto para agregarlo") Gasto gasto) throws IncompleteDataException, InvalidTextLengthException, InvalidAmmountException {
        Gasto g = gastoService.save(gasto);
        return new  ResponseEntity<>(g, HttpStatus.CREATED);
    }

    //DELETE
    @DeleteMapping ("/{id}")
    @Operation (summary = "ELiminar un gasto", description = "Elimina un gasto hecho por el usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminado"),
            @ApiResponse(responseCode = "404", description = "Error encontrando el gasto")
    })
    public ResponseEntity<Void> deleteGasto(@PathVariable @Parameter (description = "ID del gasto que se eliminara") String id) throws NotFoundException {
        gastoService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //UPDATE
    @PutMapping ("/{id}")
    @Operation (summary = "Editar un gasto", description = "Edita un gasto hecho por el usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eliminado"),
            @ApiResponse(responseCode = "404", description = "Error encontrando el gasto")
    })
    public ResponseEntity<Gasto> updateGasto(@PathVariable @Parameter (description = "ID del gasto que se actualizara") String id ,
                                             @RequestBody @Parameter (description = "Body con la nueva información del gasto") Gasto gasto) throws NotFoundException {
        gasto.setIdGasto(id);
        Gasto g = gastoService.update(gasto);
        return new ResponseEntity<>(g, HttpStatus.OK);
    }

    //LISTAR
    @GetMapping
    @Operation (summary = "Obtener todos los gastos del usuario", description = "Devuelve una lista de todos los gastos registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Gasto>> getAllGastos(){
        return new ResponseEntity<>(gastoService.getAll(), HttpStatus.OK);
    }

    //Obtener por id
    @GetMapping ("/{id}")
    @Operation (summary = "Obtener un gasto especifico por su ID", description = "Devuelve un gasto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gasto encontrado"),
            @ApiResponse(responseCode = "404", description = "Error en al encontrar el gasto")
    })
    public ResponseEntity<Gasto> getGastoById(@PathVariable @Parameter (description = "ID del gasto que se encontrara") String id) throws NotFoundException {
        Gasto g = gastoService.getByid(id);
        return new ResponseEntity<>(g, HttpStatus.OK);
    }

    //Filtrar
    @GetMapping ("/buscar")
    @Operation (summary = "Obtener una lista de gastos mediante un metodo de filtrado", description = "Devuelve una lista de gastos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Gasto>> getByFilters(@RequestParam(required = false) @Parameter (description = "El tipo de gasto por el que filtraremos")String tipoDeGasto,
                                                    @RequestParam(defaultValue = "0") @Parameter (description = "Si queremos filtrar por gastos mayores que el gasto minimo")double gastoMinimo,
                                                    @RequestParam(defaultValue = "0") @Parameter (description = "Si queremos filtrar por gastos menores que el gasto maximo")double gastoMaximo,
                                                    @RequestParam(defaultValue = "0") @Parameter (description = "Si queremos filtrar por un valor de gasto en especifico") double montoGasto,
                                                    @RequestParam(required = false) @Parameter (description = "Si queremos filtrar por un valor de gaADsto en especifico")String filtroFecha){
        List<Gasto> gastos = gastoService.getByFilters(tipoDeGasto, gastoMinimo, gastoMaximo, montoGasto, filtroFecha);
        return new ResponseEntity<>(gastos, HttpStatus.OK);
    }
}
