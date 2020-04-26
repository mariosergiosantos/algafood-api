package com.algaworks.algafood.domain.exception;

public class EstadoNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public EstadoNotFoundException(String mensagem) {
		super(mensagem);
	}

	public EstadoNotFoundException(Long estadoId) {
		super(String.format("Não existe um cadastro de estado com código %d", estadoId));
	}

}
