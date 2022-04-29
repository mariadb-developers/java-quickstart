package com.example.springbootjooq;

import static com.example.tables.ProgrammingLanguage.PROGRAMMING_LANGUAGE;

import java.util.Arrays;
import java.util.List;

import com.example.tables.records.ProgrammingLanguageRecord;

import org.jooq.DSLContext;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private final DSLContext dslContext;

	public Application(DSLContext dslContext) {
		this.dslContext = dslContext;
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

	public void deleteProgrammingLanguages() {
		System.out.println("Deleting programming languages...");
		dslContext.truncate(PROGRAMMING_LANGUAGE)
				.execute();
	}

	public void createProgrammingLanguages() {
		System.out.println("Creating programming languages...");

		Arrays.stream("Java,C++,C#,JavaScript,Rust,Go,Python,PHP".split(","))
				.forEach(name -> {
					dslContext.insertInto(PROGRAMMING_LANGUAGE)
							.columns(PROGRAMMING_LANGUAGE.NAME, PROGRAMMING_LANGUAGE.RATING)
							.values(name, (int) (Math.random() * (10 - 3 + 1)) + 3)
							.execute();
				});
	}

	public void printTopProgrammingLanguages(int rating) {
		System.out.println("Top programming languages:");

		List<ProgrammingLanguageRecord> programmingLanguages = dslContext
				.select(PROGRAMMING_LANGUAGE.NAME, PROGRAMMING_LANGUAGE.RATING)
				.from(PROGRAMMING_LANGUAGE)
				.where(PROGRAMMING_LANGUAGE.RATING.greaterThan(rating))
				.orderBy(PROGRAMMING_LANGUAGE.RATING.desc())
				.fetchInto(ProgrammingLanguageRecord.class);

		programmingLanguages.stream()
				.map(pl -> pl.getName() + ": " + pl.getRating())
				.forEach(System.out::println);
	}

}
