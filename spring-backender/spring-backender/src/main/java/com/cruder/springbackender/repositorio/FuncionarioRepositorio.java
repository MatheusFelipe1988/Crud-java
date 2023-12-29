package com.cruder.springbackender.repositorio;

import com.cruder.springbackender.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {


}
