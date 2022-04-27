package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class Application {

	private Logger log = Logger.getAnonymousLogger();

	@PersistenceContext(unitName = "microprofile-demo")
	private EntityManager entityManager;

	@Transactional
	public void run(@Observes @Initialized(ApplicationScoped.class) Object init) {
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
