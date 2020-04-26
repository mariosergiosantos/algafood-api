package com.algaworks.algafood.domain.exception;

public class CidadeNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public CidadeNotFoundException(String mensagem) {
		super(mensagem);
	}

	public CidadeNotFoundException(Long cidadeId) {
		super(String.format("Não existe um cadastro de cidade com código %d", cidadeId));
	}

}
