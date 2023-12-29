package com.cruder.springbackender.controller;

import com.cruder.springbackender.exception.ResourceNotFoundException;
import com.cruder.springbackender.model.Funcionario;
import com.cruder.springbackender.repositorio.FuncionarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController {
    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @GetMapping
    public List<Funcionario> getAllFuncionarios(){
        return funcionarioRepositorio.findAll();
    }
    //modo de criar registro do api rest
    @PostMapping
    public Funcionario createFuncionario(@RequestBody Funcionario funcionario){
        return funcionarioRepositorio.save(funcionario);
    }
    //criar o get do api rest
    @GetMapping("{id}")
    public ResponseEntity<Funcionario> getFuncionarioById(@PathVariable long id){
        Funcionario funcionario = funcionarioRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario nao existe com id:" + id));
        return ResponseEntity.ok(funcionario);
    }
    //criar o metodo update da api rest
    @PutMapping("{id}")
    public ResponseEntity<Funcionario> updateFuncionario(@PathVariable long id,@RequestBody Funcionario funcionarioDetalhes){
        Funcionario updateFuncionario = funcionarioRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario nao existe sem id: " + id));
            updateFuncionario.setNome(funcionarioDetalhes.getNome());
            updateFuncionario.setSobreNome(funcionarioDetalhes.getSobreNome());
            updateFuncionario.setEmailId(funcionarioDetalhes.getEmailId());

            funcionarioRepositorio.save(updateFuncionario);

            return ResponseEntity.ok(updateFuncionario);
    }
    //criar método deletar da api rest
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteFuncionario(@PathVariable long id){
        Funcionario funcionario = funcionarioRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario nao existe nesse id: "+ id));
        funcionarioRepositorio.delete(funcionario);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
