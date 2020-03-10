package com.algaworks.algafood.domain.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.BussinessException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import com.algaworks.algafood.domain.service.RestauranteService;

@Service
public class RestauranteServiceImpl implements RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaService cozinhaService;

	@Override
	public List<Restaurante> findAll() {
		return restauranteRepository.findAll();
	}

	@Override
	public Optional<Restaurante> findById(Long restauranteId) {
		return Optional
				.ofNullable(restauranteRepository.findById(restauranteId).orElseThrow(() -> new EntityNotFoundException(
						String.format("Restaurante com código %d não encontrado!", restauranteId))));
	}

	@Override
	public Restaurante save(Restaurante restaurante) {

		if (Objects.isNull(restaurante.getNome()) || Objects.isNull(restaurante.getCozinha())) {
			throw new BussinessException("Campos obrigatórios não preenchidos");
		}

		if (cozinhaService.findById(restaurante.getCozinha().getId()).isEmpty()) {
			throw new EntityNotFoundException("Identificador da cozinha não encontrada!");
		}

		return restauranteRepository.save(restaurante);
	}

	@Override
	public void remove(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de restaurante com código %d", restauranteId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removido, pois está em uso", restauranteId));
		}
	}

}
