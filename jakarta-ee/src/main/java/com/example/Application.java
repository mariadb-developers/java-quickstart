package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Singleton
@Startup
public class Application {

	private static final Logger log = Logger.getAnonymousLogger();

	@PersistenceContext(unitName = "jakarta-ee-demo")
	private EntityManager entityManager;

	@PostConstruct
	public void run() {
		deleteProgrammingLanguages();
		createProgrammingLanguages();
		printTopProgrammingLanguages();
	}

	@Transactional
	public void deleteProgrammingLanguages() {
		log.info("Deleting programming languages...");
		entityManager.createQuery("delete from ProgrammingLanguage pl").executeUpdate();
	}

	@Transactional
	public void createProgrammingLanguages() {
		log.info("Creating programming languages...");
		Arrays.stream("Java,C++,C#,JavaScript,Rust,Go,Python,PHP".split(","))
				.map(name -> new ProgrammingLanguage(name, (int) (Math.random() * 10)))
				.forEach(entityManager::persist);
	}

	public void printTopProgrammingLanguages() {
		log.info("Top programming languages:");

		TypedQuery<ProgrammingLanguage> query = entityManager.createNamedQuery(
				"topProgrammingLanguages", ProgrammingLanguage.class);
		query.setParameter("rating", 5);
		List<ProgrammingLanguage> programmingLanguages = query.getResultList();

		programmingLanguages.stream()
				.map(pl -> pl.getName() + ": " + pl.getRating())
				.forEach(System.out::println);
	}

}