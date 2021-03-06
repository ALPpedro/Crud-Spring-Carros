package com.concessionaria.projetocarros.Services;

import com.concessionaria.projetocarros.Repositories.MarcaDinamicoRepository;
import com.concessionaria.projetocarros.Repositories.MarcaRepository;
import com.concessionaria.projetocarros.Repositories.ModeloDinamicoRepository;
import com.concessionaria.projetocarros.dtos.MarcaDTO;
import com.concessionaria.projetocarros.exception.Error;
import com.concessionaria.projetocarros.exception.ResourceNotFoundException;
import com.concessionaria.projetocarros.models.Marca;
import com.concessionaria.projetocarros.models.Modelo;
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
public class MarcaService {
    final MarcaRepository marcaRepository;
    final ModeloDinamicoRepository modeloDinamicoRepository;
    final MarcaDinamicoRepository marcaDinamicoRepository;

    public MarcaService(MarcaRepository marcaRepository, ModeloDinamicoRepository modeloDinamicoRepository, MarcaDinamicoRepository marcaDinamicoRepository) {
        this.marcaRepository = marcaRepository;
        this.modeloDinamicoRepository = modeloDinamicoRepository;
        this.marcaDinamicoRepository = marcaDinamicoRepository;
    }

    @Transactional
    public ResponseEntity<Object> salvar(MarcaDTO marcaDTO){
        var marca = new Marca();

        BeanUtils.copyProperties(marcaDTO, marca);
        marcaRepository.save(marca);
        return ResponseEntity.ok(true);
    }
    @Transactional
    public ResponseEntity<Object> save(Marca marca){
        marcaRepository.save(marca);
        return ResponseEntity.ok(marca);
    }

    public Marca buscarids(Long id){
      Marca marca=  marcaRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id n??o encontrado"));
        return marca;
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
    public List<Marca> buscartd(){
        return marcaRepository.findAll();
    }

    public void delete(Long id){
        marcaRepository.deleteById(id);
    }

    public Optional<Marca> findById(Long id){
       return marcaRepository.findById(id);
    }

    public Page<Marca> buscartudo(String nome, Pageable pageable){
        return marcaDinamicoRepository.find1(nome, pageable);
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

    public List<Marca> buscar() {
        return marcaRepository.findAll();
    }
}
