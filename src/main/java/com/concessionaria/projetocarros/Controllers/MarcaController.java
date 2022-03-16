package com.concessionaria.projetocarros.Controllers;

import com.concessionaria.projetocarros.Services.MarcaService;
import com.concessionaria.projetocarros.dtos.MarcaDTO;
import com.concessionaria.projetocarros.exception.Error;
import com.concessionaria.projetocarros.exception.ResourceNotFoundException;
import com.concessionaria.projetocarros.models.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

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
    public ResponseEntity<?> buscarid(@PathVariable Long id){
       try {return marcaService.buscarids(id);}
       catch (ResourceNotFoundException exception) {
         Error error =  marcaService.error(exception);
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
       }
    }

    @GetMapping
    public Page<Marca> buscartudo(Pageable pageable){
       return  marcaService.buscartudo(pageable);
    }

}
