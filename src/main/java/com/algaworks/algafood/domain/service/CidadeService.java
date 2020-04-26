package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Cidade;

@Service
public interface CidadeService {

	List<Cidade> findAll();

	Cidade findById(Long cidadeId);

	Cidade save(Cidade cidadeId);

	void remove(Long cidadeId);

}
