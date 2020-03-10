package com.algaworks.algafood.domain.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.BussinessException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;
import com.algaworks.algafood.domain.service.EstadoService;

@Service
public class CidadeServiceImpl implements CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoService estadoService;

	@Override
	public List<Cidade> findAll() {
		return cidadeRepository.findAll();
	}

	@Override
	public Optional<Cidade> findById(Long cidadeId) {
		return Optional.ofNullable(cidadeRepository.findById(cidadeId).orElseThrow(
				() -> new EntityNotFoundException(String.format("Cidade com código %d não encontrado!", cidadeId))));
	}

	@Override
	public Cidade save(Cidade cidade) {
		if (Objects.isNull(cidade.getNome()) || Objects.isNull(cidade.getEstado())) {
			throw new BussinessException("Campos obrigatórios não preenchidos");
		}

		if (estadoService.findById(cidade.getEstado().getId()).isEmpty()) {
			throw new EntityNotFoundException("Identificador do estado não encontrado!");
		}

		return cidadeRepository.save(cidade);
	}

	@Override
	public void remove(Long cidadeId) {
		cidadeRepository.deleteById(cidadeId);
	}

}
