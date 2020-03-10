package com.algaworks.algafood.domain.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;

@Service
public class CozinhaServiceImpl implements CozinhaService {

	@Autowired
	CozinhaRepository cozinhaRepository;

	public List<Cozinha> findAll() {
		return cozinhaRepository.findAll();
	}

	public Optional<Cozinha> findById(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId);
	}

	public Cozinha save(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	public void remove(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser removida, pois está em uso", cozinhaId));
		}
	}

}
