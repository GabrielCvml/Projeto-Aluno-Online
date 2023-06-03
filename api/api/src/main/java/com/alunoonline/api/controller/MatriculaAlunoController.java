package com.alunoonline.api.controller;

import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.model.dtos.HistoricoAlunoDto;
import com.alunoonline.api.model.dtos.MatriculaAlunoNotasOnlyDto;
import com.alunoonline.api.service.MatriculaAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/matricula-aluno")
public class MatriculaAlunoController {

    @Autowired
    MatriculaAlunoService service;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MatriculaAluno> create(@RequestBody MatriculaAluno matriculaAluno ){
        MatriculaAluno matriculaAlunoCreated = service.create(matriculaAluno);

        return ResponseEntity.status(201).body(matriculaAlunoCreated);
    }
    @PatchMapping("/upgrade-grades/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void UpdateGrades(@RequestBody MatriculaAlunoNotasOnlyDto notasOnlyDto, @PathVariable Long id) {
        service.updateGrades(notasOnlyDto, id);
    }

    @PatchMapping("/update-status/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatusForLocked(@PathVariable Long id){
        service.updateStatusForLocked(id);
    }

    @GetMapping("/emitir-historico-aluno/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HistoricoAlunoDto emitirHistoricoAluno(@PathVariable Long id){
        return service.issueStudentHistory(id);
    }

}
