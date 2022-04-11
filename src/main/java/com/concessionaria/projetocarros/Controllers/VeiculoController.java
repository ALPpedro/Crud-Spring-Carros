package com.concessionaria.projetocarros.Controllers;

import com.concessionaria.projetocarros.Services.ModeloService;
import com.concessionaria.projetocarros.Services.VeiculoService;
import com.concessionaria.projetocarros.dtos.ModeloDTO;
import com.concessionaria.projetocarros.dtos.VeiculoDTO;
import com.concessionaria.projetocarros.exception.Error;
import com.concessionaria.projetocarros.exception.ResourceNotFoundException;
import com.concessionaria.projetocarros.models.Marca;
import com.concessionaria.projetocarros.models.Modelo;
import com.concessionaria.projetocarros.models.Veiculo;
import com.concessionaria.projetocarros.models.resposta.ModeloResposta;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final ModeloService modeloService;
    private final VeiculoService veiculoService;

    public VeiculoController(ModeloService modeloService, VeiculoService veiculoService) {
        this.modeloService = modeloService;
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<Object> incluir(@RequestBody @Valid VeiculoDTO veiculoDTO){
            return  veiculoService.salvar(veiculoDTO);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex){
        return veiculoService.handleValidationException(ex);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarid(@PathVariable Long id){
        try {return new ResponseEntity<Veiculo>(veiculoService.buscarids(id), HttpStatus.OK);}
        catch (ResourceNotFoundException exception) {
            Error error = veiculoService.error(exception);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping
    public Page<Veiculo> buscartudo(Pageable pageable){
        return  veiculoService.buscartudo(pageable);
    }

    @GetMapping("/buscar")
    public  Page buscarModelos(
            @RequestParam(value = "idMarca", required = false) Long idMarca,
            @RequestParam(value = "idModelo", required = false) Long idModelo,
            @RequestParam(value = "valorMenor", required = false,defaultValue = "0") float valorMenor,
            @RequestParam(value = "valorMaior", required = false, defaultValue = "0") float valorMaior,
            Pageable pageable
    ){
        return  veiculoService.buscartudao(idMarca, idModelo,valorMenor, valorMaior, pageable);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        Optional<Veiculo> veiculo = veiculoService.findById(id);
        if (!veiculo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não Encontramos esse veiculo");
        }
        veiculoService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Veiculo de valor: " + veiculo.get().getValor() + ", Deletado com sucesso");
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value="id")Long id,
                                         @RequestBody @Valid VeiculoDTO veiculoDTO){
        Optional<Veiculo> veiculoOptional = veiculoService.findById(id);
        Optional<Modelo> modeloOptional = modeloService.findById(veiculoDTO.getModelo().getId());
        if (!veiculoOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não Encontramos esse veiculo");
        }
        if (!modeloOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não Encontramos esse modelo");
        }
        var veiculoModel = new Veiculo();
        BeanUtils.copyProperties(veiculoDTO, veiculoModel);
        veiculoModel.setId(veiculoOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(veiculoService.save(veiculoModel));
    }

}
