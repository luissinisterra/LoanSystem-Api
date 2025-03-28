package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.model.Gasto;
import com.loansytemapi.LoanSystem_Api.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    private final GastoService gastoService;

    @Autowired
    public GastoController(GastoService gastoService) {
        this.gastoService = gastoService;
    }

    //POST
    @PostMapping
    public ResponseEntity<Gasto> saveGasto(@RequestBody Gasto gasto){
        Gasto g = gastoService.save(gasto);
        if (g != null) {
            return new ResponseEntity<>(g, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //DELETE
    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteGasto(@PathVariable String id) {
        Gasto g = gastoService.getByid(id);
        if (g != null) {
            gastoService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //UPDATE
    @PutMapping ("/{id}")
    public ResponseEntity<Gasto> updateGasto(@PathVariable String id, @RequestBody Gasto gasto){
        Gasto g = gastoService.getByid(id);
        if (g != null) {
            gasto.setIdGasto(id);
            Gasto gUpdated = gastoService.update(gasto);
            return new ResponseEntity<>(gUpdated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //LISTAR

    @GetMapping
    public ResponseEntity<List<Gasto>> getAllGastos(){
        return new ResponseEntity<>(gastoService.getAll(), HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Gasto> getGastoById(@PathVariable String id){
        Gasto g = gastoService.getByid(id);
        if (g != null) {
            return new ResponseEntity<>(g, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping ("/buscar")
    public ResponseEntity<List<Gasto>> getByFilters(@RequestParam(required = false) String tipoDeGasto,
                                                    @RequestParam(defaultValue = "0") double gastoMinimo,
                                                    @RequestParam(defaultValue = "0") double gastoMaximo,
                                                    @RequestParam(defaultValue = "0") double montoGasto,
                                                    @RequestParam(required = false) String filtroFecha){
        List<Gasto> gastos = gastoService.getByFilters(tipoDeGasto, gastoMinimo, gastoMaximo, montoGasto, filtroFecha);
        return new ResponseEntity<>(gastos, HttpStatus.OK);
    }
}
