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
		printPupularProgrammingLanguages();
	}

	@Transactional
	public void deleteProgrammingLanguages() {
		log.info("Deleting programming languages...");
		entityManager.createQuery("delete from ProgrammingLanguage pl").executeUpdate();
	}

	@Transactional
	public void createProgrammingLanguages() {
		log.info("Creating programming languages...");
		var java = new ProgrammingLanguage("Java", 10);
		entityManager.persist(java);
		Arrays.stream("C++,C#,JavaScript,Rust,Go,Python,PHP".split(","))
				.map(name -> new ProgrammingLanguage(name, (int) (Math.random() * 10)))
				.forEach(entityManager::persist);
	}

	public void printPupularProgrammingLanguages() {
		log.info("Printing popular programming languages...");
		TypedQuery<ProgrammingLanguage> query = entityManager.createNamedQuery(
				"popularProgrammingLanguages", ProgrammingLanguage.class);
		query.setParameter("rating", 5);
		List<ProgrammingLanguage> programmingLanguages = query.getResultList();
		var output = new StringBuilder("Popular programming languages:");

		programmingLanguages.stream()
				.map(pl -> "\n" + pl.getName() + ": " + pl.getRating())
				.forEach(output::append);

		log.info(output.toString());
	}

}