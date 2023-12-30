package com.cruder.springbackender;

import com.cruder.springbackender.model.Funcionario;
import com.cruder.springbackender.repositorio.FuncionarioRepositorio;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Swagger api", version = "1", description = "API desenvolvida para documentação deste projeto"))

public class SpringBackenderApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(SpringBackenderApplication.class, args);
	}
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;

	@Override
	public void run(String... args) throws Exception {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Matheus");
		funcionario.setSobreNome("Fel");
		funcionario.setEmailId("fel.com");
		funcionarioRepositorio.save(funcionario);

		Funcionario funcionario1 = new Funcionario();
		funcionario1.setNome("Max");
		funcionario1.setSobreNome("Verstapeen");
		funcionario1.setEmailId("max.com");
		funcionarioRepositorio.save(funcionario1);

		Funcionario funcionario2 = new Funcionario();
		funcionario2.setNome("Max");
		funcionario2.setSobreNome("Mosley");
		funcionario2.setEmailId("maax.com");
		funcionarioRepositorio.save(funcionario2);
	}
}
