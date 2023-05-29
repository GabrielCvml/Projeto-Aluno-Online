package com.alunoonline.api.service;

import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository repository;
    //Criar
    public Aluno create(Aluno aluno){
        return repository.save(aluno);
    }
    //Listar todos
    public List<Aluno> findAll(){
        return repository.findAll();
    }
    //Listar único
    public Optional<Aluno> findById(Long id){
        return repository.findById(id);
    }
    //Atualizar
    public Aluno update(Long id, Aluno alunoUpdate){
        return repository.findById(id).map(aluno -> {
            aluno.setNome(alunoUpdate.getNome());
            aluno.setEmail(alunoUpdate.getEmail());
            aluno.setCurso(alunoUpdate.getCurso());
            return repository.save(aluno);
        }).orElseThrow(() -> new RuntimeException("Id não encontrado"));
    }

    //Deletar
    public void delete(Long id){
        repository.deleteById(id);
    }

}
