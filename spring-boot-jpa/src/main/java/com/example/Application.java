package com.example;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private final ProgrammingLanguageRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public Application(ProgrammingLanguageRepository repository) {
		this.repository = repository;
	}

	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> {
			createProgrammingLanguages();
			printTopProgrammingLanguages();
		};
	}

	private void createProgrammingLanguages() {
		Arrays.stream("Java,C++,C#,JavaScript,Rust,Go,Python,PHP".split(","))
				.map(name -> new ProgrammingLanguage(name, (int) (Math.random() * 10)))
				.forEach(repository::save);
	}

	private void printTopProgrammingLanguages() {
		List<ProgrammingLanguage> programmingLanguages = repository.findAll();

		programmingLanguages.stream()
				.map(pl -> pl.getName() + ": " + pl.getRating())
				.forEach(System.out::println);
	}

}
