package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Restaurante;

@Service
public interface RestauranteService {

	List<Restaurante> findAll();

	Restaurante findById(Long restauranteId);

	Restaurante save(Restaurante restaurante);

	void remove(Long restauranteId);

}
