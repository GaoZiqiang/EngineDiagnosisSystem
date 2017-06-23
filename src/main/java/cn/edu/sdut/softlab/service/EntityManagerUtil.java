package cn.edu.sdut.softlab.service;

import java.util.Date;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class EntityManagerUtil<T> {
	// EntityManagerFactory emf;
	@Inject
	EntityManagerFactory emf;
	@Inject
	EntityManager em;

	//entityManagerFactory
	/*public void entityManagerFactory() {
		try {
			emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");
			em = emf.createEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	public void create(T entity) {
		em.persist(entity);
	}

	/*public EntityManager getEntityManager() {
		try {
			emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");

			em = emf.createEntityManager();
			// em.persist(engineInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return em;
	}*/

}
