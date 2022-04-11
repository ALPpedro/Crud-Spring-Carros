package com.concessionaria.projetocarros.Services;

import com.concessionaria.projetocarros.Repositories.MarcaRepository;
import com.concessionaria.projetocarros.Repositories.ModeloDinamicoRepository;
import com.concessionaria.projetocarros.Repositories.ModeloRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ModeloService {
    private final ModeloRepository modeloRepository;
    private final ModeloDinamicoRepository modeloDinamicoRepository;
    private final MarcaRepository marcaRepository;


    public ModeloService(ModeloRepository modeloRepository, ModeloDinamicoRepository modeloDinamicoRepository, MarcaRepository marcaRepository) {
        this.modeloRepository = modeloRepository;
        this.modeloDinamicoRepository = modeloDinamicoRepository;
        this.marcaRepository = marcaRepository;
    }

    @Transactional
    public ResponseEntity<Object> salvar(ModeloDTO modeloDTO){
        var modelo = new Modelo();
        BeanUtils.copyProperties(modeloDTO, modelo);
        modeloRepository.save(modelo);
        return ResponseEntity.ok(modelo);
    }
    public Optional<Modelo> findById(Long id){
        return modeloRepository.findById(id);
    }

    public Modelo buscarids(Long id){
        Modelo modelo =  modeloRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
        return modelo;
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

    public List<Modelo> buscartudo(){
        return modeloRepository.findAll();
    }

    public Page<ModeloResposta> buscartudao(Long idMarca, String modelo, Pageable pageable) {
        return modeloDinamicoRepository.find1(modelo, idMarca, pageable);
    }

    public void delete(Long id){
        modeloRepository.deleteById(id);
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
    public ResponseEntity<Object> save(Modelo modelo){
        modeloRepository.save(modelo);
        return ResponseEntity.ok(modelo);
    }
}
