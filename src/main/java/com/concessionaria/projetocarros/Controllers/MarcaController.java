package com.concessionaria.projetocarros.Controllers;

import com.concessionaria.projetocarros.Services.MarcaService;
import com.concessionaria.projetocarros.dtos.MarcaDTO;
import com.concessionaria.projetocarros.models.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/marcas")
public class MarcaController {

    @Autowired
    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @PostMapping
    public ResponseEntity<Object> incluir(@RequestBody @Valid MarcaDTO marcaDTO){
        return  marcaService.salvar(marcaDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Marca> buscarid(@PathVariable Long id){
       return marcaService.buscarids(id);
    }

}
