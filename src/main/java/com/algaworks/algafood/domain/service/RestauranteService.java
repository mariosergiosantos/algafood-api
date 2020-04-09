package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Restaurante;

@Service
public interface RestauranteService {

	List<Restaurante> findAll();

	Optional<Restaurante> findById(Long restauranteId);

	Restaurante save(Restaurante restaurante);
	
	Restaurante update(Restaurante restaurante);

	void remove(Long restauranteId);

}
