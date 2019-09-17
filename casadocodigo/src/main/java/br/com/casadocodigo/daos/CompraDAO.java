package br.com.casadocodigo.daos;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.models.Compra;

public class CompraDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager entityManager;

	public void save(Compra compra) {
		entityManager.persist(compra);
	}

	public Compra findByUuid(String uuid) {
		return entityManager.createQuery("select c from Compra c where c.uuid= :uuid", Compra.class).setParameter("uuid", uuid).getSingleResult();
	}
}
