package com.concessionaria.projetocarros.Services;

import com.concessionaria.projetocarros.Repositories.MarcaRepository;
import com.concessionaria.projetocarros.dtos.MarcaDTO;
import com.concessionaria.projetocarros.exception.ResourceNotFoundException;
import com.concessionaria.projetocarros.models.Marca;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MarcaService {
    final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Transactional
    public ResponseEntity<Object> salvar(MarcaDTO marcaDTO){
        var marca = new Marca();
        BeanUtils.copyProperties(marcaDTO, marca);
        marcaRepository.save(marca);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<Marca> buscarids(Long id){
      Marca marca=  marcaRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
        return new ResponseEntity<Marca>(marca,HttpStatus.OK);
    }

}
