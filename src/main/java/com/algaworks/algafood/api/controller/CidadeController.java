package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.BadRequestException;
import com.algaworks.algafood.domain.exception.EstadoNotFoundException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

@RestController
@RequestMapping("cidades")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;

	@GetMapping
	public List<Cidade> listar() {
		return cidadeService.findAll();
	}

	@GetMapping("/{cidadeId}")
	public Cidade find(@PathVariable Long cidadeId) {
		return cidadeService.findById(cidadeId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade save(@RequestBody Cidade cidade) {
		try {
			return cidadeService.save(cidade);
		} catch (EstadoNotFoundException e) {
			throw new BadRequestException(e.getMessage(), e);
		}
	}

	@PutMapping("/{cidadeId}")
	public Cidade update(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
		Cidade cidadeAtual = cidadeService.findById(cidadeId);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		try {
			return cidadeService.save(cidade);
		} catch (EstadoNotFoundException e) {
			throw new BadRequestException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long cidadeId) {
		cidadeService.remove(cidadeId);
	}

}
