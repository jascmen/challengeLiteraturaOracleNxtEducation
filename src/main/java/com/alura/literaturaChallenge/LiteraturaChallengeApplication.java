package com.alura.literaturaChallenge;

import com.alura.literaturaChallenge.Main.Principal;
import com.alura.literaturaChallenge.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaChallengeApplication implements CommandLineRunner {

	@Autowired
	private Principal principal;

	public static void main(String[] args)  {
		SpringApplication.run(LiteraturaChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.mostrarMenu();
	}
}
