package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class RestauranteRepositoryImpl implements RestauranteRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurante> all() {
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}

	@Override
	public Restaurante findById(Long id) {
		return manager.find(Restaurante.class, id);
	}

	@Override
	@Transactional
	public Restaurante save(Restaurante restaurante) {
		return manager.merge(restaurante);
	}

	@Override
	@Transactional
	public void remove(Restaurante restaurante) {
		restaurante = this.findById(restaurante.getId());
		manager.remove(restaurante);
	}

}
