package com.algaworks.algafood.domain.exception;

public class BussinessException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public BussinessException(String mensagem) {
		super(mensagem);
	}

}
