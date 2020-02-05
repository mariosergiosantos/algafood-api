package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository {

	List<Restaurante> all();

	Restaurante findById(Long id);

	Restaurante save(Restaurante restaurante);

	void remove(Restaurante restaurante);

}
