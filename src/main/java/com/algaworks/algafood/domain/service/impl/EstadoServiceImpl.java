package com.algaworks.algafood.domain.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;

@Service
public class EstadoServiceImpl implements EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	@Override
	public List<Estado> findAll() {
		return estadoRepository.findAll();
	}

	@Override
	public Optional<Estado> findById(Long estadoId) {
		return Optional.ofNullable(estadoRepository.findById(estadoId).orElseThrow(
				() -> new EntityNotFoundException(String.format("Estado com código %d não encontrado!", estadoId))));
	}

	@Override
	public Estado save(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Override
	public void remove(Long estadoId) {
		estadoRepository.deleteById(estadoId);
	}

}
