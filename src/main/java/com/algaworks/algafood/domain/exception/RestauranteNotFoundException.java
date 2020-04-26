package com.algaworks.algafood.domain.exception;

public class RestauranteNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public RestauranteNotFoundException(String mensagem) {
		super(mensagem);
	}

	public RestauranteNotFoundException(Long restauranteId) {
		super(String.format("Não existe um cadastro de restaurante com código %d", restauranteId));
	}

}
