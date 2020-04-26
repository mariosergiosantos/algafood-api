package com.algaworks.algafood.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CozinhaNotFoundException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;

@Service
public class CozinhaServiceImpl implements CozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

	@Autowired
	CozinhaRepository cozinhaRepository;

	public List<Cozinha> findAll() {
		return cozinhaRepository.findAll();
	}

	public Cozinha findById(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new CozinhaNotFoundException(cozinhaId));
	}

	public Cozinha save(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	public void remove(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNotFoundException(cozinhaId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	}

}
