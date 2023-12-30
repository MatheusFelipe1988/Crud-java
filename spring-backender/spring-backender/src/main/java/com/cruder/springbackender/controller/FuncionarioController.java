package com.cruder.springbackender.controller;

import com.cruder.springbackender.exception.ResourceNotFoundException;
import com.cruder.springbackender.model.Funcionario;
import com.cruder.springbackender.repositorio.FuncionarioRepositorio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/funcionarios", produces = {"application/json"})
@Tag(name = "open-api")
public class FuncionarioController{
    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;



    @Operation(summary = "Realizar a busca por funcionarios cadastrados",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "busca realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro na busca"),
    })


    @GetMapping
    public List<Funcionario> getAllFuncionarios(){
        return funcionarioRepositorio.findAll();
    }

    @Operation(summary = "Realiza o upload de arquivos",method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "metodo de enviar um dado"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição invalida"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos"),
            @ApiResponse(responseCode = "500", description = "Error"),
    })


    @PostMapping
    public Funcionario createFuncionario(@RequestBody Funcionario funcionario){
        return funcionarioRepositorio.save(funcionario);
    }

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
