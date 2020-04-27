package com.algaworks.algafood.domain.exception;

public abstract class NotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String mensagem) {
		super(mensagem);
	}
	
	public NotFoundException(String mensagem, Throwable cause) {
		super(mensagem, cause);
	}

}
