package com.alunoonline.api.service;

import com.alunoonline.api.model.Professor;
import com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository repository;
    //Criar
    public Professor create(Professor professor){
        return repository.save(professor);
    }
    //Listar todos
    public List<Professor> findAll(){
        return repository.findAll();
    }
    //Listar único
    public Optional<Professor> findById(Long id){
        return repository.findById(id);
    }
    //Atualizar
    public Professor update(Long id, Professor professorUpdate){
        return repository.findById(id).map(professor -> {
            professor.setNome(professorUpdate.getNome());
            professor.setEmail(professorUpdate.getEmail());

            return repository.save(professor);
        }).orElseThrow(()-> new RuntimeException("id não encontrado"));
    }

    //Deletar
    public void delete(Long id){
        repository.deleteById(id);
    }

}
