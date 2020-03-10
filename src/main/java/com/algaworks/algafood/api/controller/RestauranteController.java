package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteService.findAll();
	}

	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Optional<Restaurante> restaurante = restauranteService.findById(restauranteId);

		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> save(@RequestBody Restaurante restaurante) {

		try {
			restaurante = restauranteService.save(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> update(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {

		try {
			return ResponseEntity.ok(restauranteService.save(restaurante));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> updateParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> request) {
		Optional<Restaurante> restauranteAtual = restauranteService.findById(restauranteId);
		merge(request, restauranteAtual.get());
		return update(restauranteId, restauranteAtual.get());
	}

	private void merge(Map<String, Object> request, Restaurante destino) {
		Restaurante restauranteOrigem = new ObjectMapper().convertValue(request, Restaurante.class);

		request.forEach((chave, valor) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, chave);
			field.setAccessible(true);
			ReflectionUtils.setField(field, destino, ReflectionUtils.getField(field, restauranteOrigem));
		});
	}

	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> deletar(@PathVariable Long restauranteId) {
		try {
			restauranteService.remove(restauranteId);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.noContent().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}

}
