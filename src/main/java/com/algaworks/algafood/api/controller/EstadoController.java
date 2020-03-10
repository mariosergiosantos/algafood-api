package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;

@RestController
@RequestMapping("estados")
public class EstadoController {

	@Autowired
	private EstadoService estadoService;

	@GetMapping
	public List<Estado> listar() {
		return estadoService.findAll();
	}

	@GetMapping("/{estadoId}")
	public ResponseEntity<Estado> find(@PathVariable Long estadoId) {
		Optional<Estado> estado = estadoService.findById(estadoId);

		if (estado.isPresent()) {
			return ResponseEntity.ok(estado.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		return estadoService.save(estado);
	}

	@PutMapping("/{estadoId}")
	public ResponseEntity<?> update(@PathVariable Long estadoId, @RequestBody Estado estado) {
		try {
			estadoService.findById(estadoId).orElseThrow(
					() -> new EntityNotFoundException(String.format("Estado com código %d não encontrado!", estadoId)));
			return ResponseEntity.ok(estadoService.save(estado));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> remove(@PathVariable Long estadoId) {
		try {
			estadoService.remove(estadoId);
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.badRequest()
					.body(String.format("Estado com código %d não pode ser removido pois está em uso!", estadoId));
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.badRequest().body(String.format("Estado com código %d não encontrado!", estadoId));
		}

		return ResponseEntity.noContent().build();

	}

}
