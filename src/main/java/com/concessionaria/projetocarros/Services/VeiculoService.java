package com.concessionaria.projetocarros.Services;

import com.concessionaria.projetocarros.Repositories.ModeloRepository;
import com.concessionaria.projetocarros.Repositories.VeiculoDinamicoRepository;
import com.concessionaria.projetocarros.Repositories.VeiculoRepository;
import com.concessionaria.projetocarros.dtos.VeiculoDTO;
import com.concessionaria.projetocarros.exception.Error;
import com.concessionaria.projetocarros.exception.ResourceNotFoundException;
import com.concessionaria.projetocarros.models.Modelo;
import com.concessionaria.projetocarros.models.Veiculo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class VeiculoService {

    private final ModeloRepository modeloRepository;
    private final VeiculoRepository veiculoRepository;
    private final VeiculoDinamicoRepository veiculoDinamicoRepository;

    public VeiculoService(ModeloRepository modeloRepository, VeiculoRepository veiculoRepository, VeiculoDinamicoRepository veiculoDinamicoRepository) {
        this.modeloRepository = modeloRepository;
        this.veiculoRepository = veiculoRepository;
        this.veiculoDinamicoRepository = veiculoDinamicoRepository;
    }

    @Transactional
    public ResponseEntity<Object> salvar(VeiculoDTO veiculoDTO){
        var veiculo = new Veiculo();
        BeanUtils.copyProperties(veiculoDTO, veiculo);
        veiculoRepository.save(veiculo);
        return ResponseEntity.ok(true);
    }

    public Veiculo buscarids(Long id){
        Veiculo veiculo =  veiculoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
        return veiculo;
    }

    public Error error(ResourceNotFoundException exception){
        Error error = new Error();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Resource not found");
        error.setMessage(exception.getMessage());
        error.setPath("test");
        return error;
    }

    public Page<Veiculo> buscartudo(Pageable pageable){
        return veiculoRepository.findAll(pageable);
    }

    public Page buscartudao(Long idMarca, Long idModelo,float valorMenor, float valorMaior,Pageable pageable) {
        return veiculoDinamicoRepository.find1(idMarca, idModelo, valorMenor, valorMaior, pageable);
    }

    public Optional<Veiculo> findById(Long id){
        return veiculoRepository.findById(id);
    }

    public void delete(Long id) {
        veiculoRepository.deleteById(id);
    }

    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) ->{
                    String fildName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fildName, errorMessage);
                }
        );

        return errors;
    }

    @Transactional
    public ResponseEntity<Object> save(Veiculo veiculo){
        veiculoRepository.save(veiculo);
        return ResponseEntity.ok(veiculo);
    }
}
