package com.algaworks.algafood.domain.exception;

public class CozinhaNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public CozinhaNotFoundException(String mensagem) {
		super(mensagem);
	}

	public CozinhaNotFoundException(Long cozinhaId) {
		super(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
	}

}
