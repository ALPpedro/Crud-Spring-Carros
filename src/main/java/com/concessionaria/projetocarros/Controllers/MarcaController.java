package com.concessionaria.projetocarros.Controllers;

import com.concessionaria.projetocarros.Services.MarcaService;
import com.concessionaria.projetocarros.dtos.MarcaDTO;
import com.concessionaria.projetocarros.exception.Error;
import com.concessionaria.projetocarros.exception.ResourceNotFoundException;
import com.concessionaria.projetocarros.models.Marca;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    public ResponseEntity<?> buscarid(@PathVariable Long id){
       try {return new ResponseEntity<Marca>(marcaService.buscarids(id), HttpStatus.OK) ;}
       catch (ResourceNotFoundException exception) {
         Error error =  marcaService.error(exception);
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
       }
    }

    @GetMapping
    public List<Marca> buscar(){
        return  marcaService.buscar();
    }

    @GetMapping("/buscar")
    public Page<Marca> buscartudo(
            @RequestParam(value = "nome", required = false) String nome,Pageable pageable){
       return  marcaService.buscartudo(nome, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value="id")Long id,
                                         @RequestBody @Valid MarcaDTO marcaDTO){
        Optional<Marca> marca = marcaService.findById(id);
        if (!marca.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não Encontramos essa marca");
        }
        var marcaModel = new Marca();
        BeanUtils.copyProperties(marcaDTO, marcaModel);
        marcaModel.setId(marca.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(marcaService.save(marcaModel));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            Optional<Marca> marca = marcaService.findById(id);
            if (!marca.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não Encontramos essa marca");
            }
            marcaService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Marca: " + marca.get().getNome() + ", Deletada com sucesso");
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não podemos excluir essa marca," +
                    "pois existe modelos cadastrados nela");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex){
        return marcaService.handleValidationException(ex);
    }


}
