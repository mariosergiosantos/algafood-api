package com.algaworks.algafood.domain.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.BadRequestException;
import com.algaworks.algafood.domain.exception.CidadeNotFoundException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;
import com.algaworks.algafood.domain.service.EstadoService;

@Service
public class CidadeServiceImpl implements CidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoService estadoService;

	@Override
	public List<Cidade> findAll() {
		return cidadeRepository.findAll();
	}

	@Override
	public Cidade findById(Long cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(() -> new CidadeNotFoundException(cidadeId));

	}

	@Override
	public Cidade save(Cidade cidade) {
		if (Objects.isNull(cidade.getNome()) || Objects.isNull(cidade.getEstado())) {
			throw new BadRequestException("Campos obrigatórios não preenchidos");
		}

		Estado estado = estadoService.findById(cidade.getEstado().getId());
		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
	}

	@Override
	public void remove(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNotFoundException(cidadeId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}

}
