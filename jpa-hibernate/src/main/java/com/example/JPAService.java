package com.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.function.Function;

/**
 * A singleton implementation that encapsulates JPA logic.
 * You can configure the database connection in the
 * {@link /src/main/resources/META-INF/persistence.xml} file.
 */
public class JPAService {

	private static JPAService instance;
	private EntityManagerFactory entityManagerFactory;

	private JPAService() {
		entityManagerFactory = Persistence.createEntityManagerFactory("jpa-demo-local");
	}

	public static synchronized JPAService getInstance() {
		return instance == null ? instance = new JPAService() : instance;
	}

	public void shutdown() {
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
			instance = null;
		}
	}

	/**
	 * Runs the specified function inside a transaction boundary. The function has
	 * access to a ready to use {@link EntityManager} and can return any type of
	 * value ({@code T}).
	 * 
	 * @param function The function to run.
	 * @param <T>      The function's return type.
	 * @return the value returned by the specified function.
	 */
	public <T> T runInTransaction(Function<EntityManager, T> function) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		boolean success = false;
		try {
			T returnValue = function.apply(entityManager);
			success = true;
			return returnValue;

		} finally {
			if (success) {
				transaction.commit();
			} else {
				transaction.rollback();
			}
		}
	}

}
