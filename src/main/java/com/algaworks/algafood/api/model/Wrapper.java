package com.algaworks.algafood.api.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(value = Include.NON_NULL)
@Getter
@Builder
public class Wrapper {

	private int status;
	private String type;
	private String title;
	private String detail;
	private LocalDateTime date;

}
