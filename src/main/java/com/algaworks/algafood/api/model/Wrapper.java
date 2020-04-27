package com.algaworks.algafood.api.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Wrapper {

	private LocalDateTime dataHora;
	private String mensagem;

}
