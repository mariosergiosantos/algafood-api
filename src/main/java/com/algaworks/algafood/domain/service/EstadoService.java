package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Estado;

@Service
public interface EstadoService {
	
	List<Estado> findAll();

	Optional<Estado> findById(Long estadoId);

	Estado save(Estado estado);

	void remove(Long estadoId);

}
