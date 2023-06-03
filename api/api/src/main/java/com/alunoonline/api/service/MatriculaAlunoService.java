package com.alunoonline.api.service;

import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.model.dtos.DisciplinaAlunoDto;
import com.alunoonline.api.model.dtos.HistoricoAlunoDto;
import com.alunoonline.api.model.dtos.MatriculaAlunoNotasOnlyDto;
import com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatriculaAlunoService {

    static final Double GradeAVGToApprove = 7.0;

    @Autowired
    MatriculaAlunoRepository repository;

    public MatriculaAluno create(MatriculaAluno matriculaAluno){
        matriculaAluno.setStatus("MATRICULADO");
        return repository.save(matriculaAluno);
    }

    public void updateGrades(MatriculaAlunoNotasOnlyDto matriculaAlunoNotasOnlyDto,Long matriculaAlunoId) {
        Optional<MatriculaAluno> matriculaAlunoToUpdate = repository.findById(matriculaAlunoId);

        if(matriculaAlunoToUpdate.isPresent()){
            MatriculaAluno matriculaAluno = matriculaAlunoToUpdate.get();

            if(matriculaAlunoNotasOnlyDto.getNota1() != null){
                matriculaAluno.setNota1(matriculaAlunoNotasOnlyDto.getNota1());
            }

            if(matriculaAlunoNotasOnlyDto.getNota2() != null){
                matriculaAluno.setNota2(matriculaAlunoNotasOnlyDto.getNota2());
            }

            if(matriculaAluno.getNota1() != null && matriculaAluno.getNota2() != null){

                double average = (matriculaAluno.getNota1() + matriculaAluno.getNota2()) / 2.0;

                if (average >= GradeAVGToApprove) {
                    matriculaAluno.setStatus("APROVADO");
                }else {
                    matriculaAluno.setStatus("REPROVADO");
                }
            }

            repository.save(matriculaAluno);
        }
    }

    public void updateStatusForLocked(Long matriculaAlunoId){
        Optional<MatriculaAluno> matriculaAlunoUp = repository.findById(matriculaAlunoId);

        if (matriculaAlunoUp.isPresent()) {
            MatriculaAluno matriculaAluno = matriculaAlunoUp.get();
            String currenStatus = matriculaAluno.getStatus();

            if (currenStatus.equals("MATRICULADO")){
                matriculaAluno.setStatus("TRANCADO");
                repository.save(matriculaAluno);
            }else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só possível trancar alunos com status matriculor");
            }
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada");
        }

    }

    public HistoricoAlunoDto issueStudentHistory(Long alunoId){
        List<MatriculaAluno> matriculaAlunoList = repository.findByAlunoId(alunoId);


        if (!matriculaAlunoList.isEmpty()){
            HistoricoAlunoDto historico = new HistoricoAlunoDto();

            historico.setNomeAluno(matriculaAlunoList.get(0).getAluno().getNome());
            historico.setCursoAluno(matriculaAlunoList.get(0).getAluno().getCurso());

            List<DisciplinaAlunoDto> disciplinaList = new ArrayList<>();

            for (MatriculaAluno matricula : matriculaAlunoList){
                DisciplinaAlunoDto disciplinaAlunoDto = new DisciplinaAlunoDto();

                disciplinaAlunoDto.setNomeDisciplina(matricula.getDisciplina().getNome());
                disciplinaAlunoDto.setNomeProfessorDisciplina(matricula.getDisciplina().getProfessor().getNome());
                disciplinaAlunoDto.setNota1(matricula.getNota1());
                disciplinaAlunoDto.setNota2(matricula.getNota2());

                if (matricula.getNota1() != null && matricula.getNota2() != null){
                    disciplinaAlunoDto.setMedia((matricula.getNota1()+ matricula.getNota2()) / 2.0);
                }else{
                    disciplinaAlunoDto.setMedia(null);
                }
                disciplinaAlunoDto.setStatus(matricula.getStatus());

                disciplinaList.add(disciplinaAlunoDto);

            }

            historico.setDisciplinaAlunoDtoList(disciplinaList);
            return historico;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Esse aluno não existe");
        }
    }
}
