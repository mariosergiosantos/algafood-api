package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Cidade;

@Service
public interface CidadeService {

	List<Cidade> findAll();

	Optional<Cidade> findById(Long cidadeId);

	Cidade save(Cidade cidadeId);

	void remove(Long cidadeId);

}
