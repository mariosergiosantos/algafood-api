package com.algaworks.algafood.api.exception_handle;

import lombok.Getter;

@Getter
public enum ProblemType {

	ENTIDADE_NAO_ENONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	BUSINESS_EXCEPTION("/erro-generico", "Violação das regras de negócio"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível");

	private String title;
	private String uri;
	private static final String HOST = "https://algafood.com.br%s";

	ProblemType(String path, String title) {
		this.uri = String.format(HOST, path);
		this.title = title;
	}

}
