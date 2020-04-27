package com.algaworks.algafood.domain.exception;

public class EntidadeEmUsoException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public EntidadeEmUsoException(String mensagem) {
		super(mensagem);
	}
	
	public EntidadeEmUsoException(String mensagem, Throwable cause) {
		super(mensagem, cause);
	}

}
