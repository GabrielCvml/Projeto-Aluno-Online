package com.alunoonline.api.controller;

import com.alunoonline.api.model.Professor;
import com.alunoonline.api.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    ProfessorService service;
    //Criar ++
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Professor> create(@RequestBody Professor professor){
        Professor professorCreate = service.create(professor);

        return ResponseEntity.status(201).body(professorCreate);
    }
    //Listar todos ++
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Professor> findAll(){
        return service.findAll();
    }
    //Listar único ++
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Professor> findById(@PathVariable Long id){
        return service.findById(id);
    }
    //Atualizar ++
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Professor update(@PathVariable Long id, @RequestBody Professor professorUpdate ) {
        Professor professor = service.update(id,professorUpdate);

        return ResponseEntity.status(200).body(professor).getBody();
    }
    //Deletar ++
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
