package com.example;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private final ProgrammingLanguageMapper programmingLanguageMapper;

	public Application(ProgrammingLanguageMapper programmingLanguageMapper) {
		this.programmingLanguageMapper = programmingLanguageMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> {
			deleteProgrammingLanguages();
			createProgrammingLanguages();
			printTopProgrammingLanguages(5);
		};
	}

	private void deleteProgrammingLanguages() {
		System.out.println("Deleting programming languages...");
		programmingLanguageMapper.deleteAll();
	}

	private void createProgrammingLanguages() {
		System.out.println("Creating programming languages...");
		Arrays.stream("Java,C++,C#,JavaScript,Rust,Go,Python,PHP".split(","))
				.forEach(name -> programmingLanguageMapper.insert(name, (int) (Math.random() * (10 - 3 + 1)) + 3));
	}

	private void printTopProgrammingLanguages(int rating) {
		System.out.println("Top programming languages:");

		List<ProgrammingLanguage> programmingLanguages = programmingLanguageMapper.findTopProgrammingLanguages(5);
		programmingLanguages.stream()
				.map(pl -> pl.getPlName() + ": " + pl.getPlRating())
				.forEach(System.out::println);
	}

}
