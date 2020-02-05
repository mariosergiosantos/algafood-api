package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository {

	List<Cozinha> all();

	Cozinha findById(Long id);

	Cozinha save(Cozinha cozinha);

	void remove(Cozinha cozinha);

}
