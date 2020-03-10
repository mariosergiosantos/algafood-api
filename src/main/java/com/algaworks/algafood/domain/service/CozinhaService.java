package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Cozinha;

@Service
public interface CozinhaService {

	List<Cozinha> findAll();

	Optional<Cozinha> findById(Long cozinhaId);

	Cozinha save(Cozinha cozinha);

	void remove(Long cozinhaId);

}
