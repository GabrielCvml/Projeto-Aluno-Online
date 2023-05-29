package com.alunoonline.api.service;

import com.alunoonline.api.model.Disciplina;
import com.alunoonline.api.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository repository;

    //Criar
    public Disciplina create(Disciplina disciplina){
        return repository.save(disciplina);
    }
    //Listar todos
    public List<Disciplina> findAll(){
        return repository.findAll();
    }
    //Listar único
    public Optional<Disciplina> findById(Long id){
        return repository.findById(id);
    }
    //Atualizar
    public Disciplina update(Long id, Disciplina disciplinaUpdate){
        return repository.findById(id).map(disciplina -> {
            disciplina.setNome(disciplinaUpdate.getNome());
            disciplina.setProfessor(disciplinaUpdate.getProfessor());
            return repository.save(disciplina);
        }).orElseThrow(() -> new RuntimeException("Id não encontrado"));
    }
    //Deletar
    public void delete(Long id){
        repository.deleteById(id);
    }
}
