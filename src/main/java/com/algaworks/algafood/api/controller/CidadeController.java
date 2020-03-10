package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.BussinessException;
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
	public ResponseEntity<Cidade> find(@PathVariable Long cidadeId) {
		Optional<Cidade> cidade = cidadeService.findById(cidadeId);

		if (cidade.isPresent()) {
			return ResponseEntity.ok(cidade.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> save(@RequestBody Cidade cidade) {
		try {
			return ResponseEntity.ok(cidadeService.save(cidade));
		} catch (EntityNotFoundException | BussinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{cidadeId}")
	public ResponseEntity<?> update(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
		try {
			cidadeService.save(cidade);
			return ResponseEntity.ok(cidade);
		} catch (EntityNotFoundException | BussinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<?> deletar(@PathVariable Long cidadeId) {
		try {
			cidadeService.remove(cidadeId);
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.badRequest().body(String.format("Cidade com código %d não encontrado!", cidadeId));
		}

		return ResponseEntity.noContent().build();

	}

}
