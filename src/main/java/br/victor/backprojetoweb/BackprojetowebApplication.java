package br.victor.backprojetoweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BackprojetowebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackprojetowebApplication.class, args);
	}

}
