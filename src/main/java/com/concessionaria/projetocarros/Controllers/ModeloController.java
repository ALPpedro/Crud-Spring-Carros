package com.concessionaria.projetocarros.Controllers;

import com.concessionaria.projetocarros.Services.MarcaService;
import com.concessionaria.projetocarros.Services.ModeloService;
import com.concessionaria.projetocarros.dtos.MarcaDTO;
import com.concessionaria.projetocarros.dtos.ModeloDTO;
import com.concessionaria.projetocarros.exception.Error;
import com.concessionaria.projetocarros.exception.ResourceNotFoundException;
import com.concessionaria.projetocarros.models.Marca;
import com.concessionaria.projetocarros.models.Modelo;
import com.concessionaria.projetocarros.models.resposta.ModeloResposta;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/modelos")
public class ModeloController {
    private final ModeloService modeloService;
    private final MarcaService marcaService;

    public ModeloController(ModeloService modeloService, MarcaService marcaService) {
        this.modeloService = modeloService;
        this.marcaService = marcaService;
    }

    @PostMapping
    public ResponseEntity<Object> incluir(@RequestBody @Valid ModeloDTO modeloDTO){
            return  modeloService.salvar(modeloDTO);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex){
        return modeloService.handleValidationException(ex);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarid(@PathVariable Long id){
        try {return new ResponseEntity<Modelo>(modeloService.buscarids(id), HttpStatus.OK);}
        catch (ResourceNotFoundException exception) {
            Error error = modeloService.error(exception);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping
    public List<Modelo> buscar(){
        return  modeloService.buscartudo();
    }

    @GetMapping("/buscar")
    public  Page<ModeloResposta> buscarModelos(
            @RequestParam(value = "idMarca", required = false) Long idMarca,
            @RequestParam(value = "modelo", required = false) String modelo,
            Pageable pageable
    ){
        return  modeloService.buscartudao(idMarca, modelo, pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        try {
            Optional<Modelo> modelo = modeloService.findById(id);
            if (!modelo.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não Encontramos esse modelo");
            }
            modeloService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Modelo: " + modelo.get().getNomeModelo() + ", Deletado com sucesso");
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não podemos excluir esse modelo," +
                    "pois existe veiculos cadastrados nela");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value="id")Long id,
                                         @RequestBody @Valid ModeloDTO modeloDTO){
        Optional<Modelo> modeloOptional = modeloService.findById(id);
        Optional<Marca> marcaOptional = marcaService.findById(modeloDTO.getMarca().getId());
        if (!modeloOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não Encontramos esse modelo");
        }
        if (!marcaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não Encontramos essa marca");
        }
        var modeloModel = new Modelo();
        BeanUtils.copyProperties(modeloDTO, modeloModel);
        modeloModel.setId(modeloOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(modeloService.save(modeloModel));
    }

}
