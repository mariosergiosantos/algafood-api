package com.algaworks.algafood.api.exception_handle;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.api.model.Wrapper;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class ApiExceptionHandle extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}

		String detail = "O corpo da requsição está inválido. Verifique erros de sintaxe.";
		Wrapper wrapper = createWrapper(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail).build();
		return this.handleExceptionInternal(ex, wrapper, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(Reference::getFieldName).collect(Collectors.joining("."));

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Wrapper wrapper = createWrapper(status, problemType, detail).build();

		return handleExceptionInternal(ex, wrapper, headers, status, request);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBadRequestException(BusinessException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Wrapper wrapper = createWrapper(status, ProblemType.BUSINESS_EXCEPTION, ex.getMessage()).build();
		return this.handleExceptionInternal(ex, wrapper, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Wrapper wrapper = createWrapper(status, ProblemType.ENTIDADE_NAO_ENONTRADA, ex.getMessage()).build();
		return this.handleExceptionInternal(ex, wrapper, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<Object> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		Wrapper wrapper = createWrapper(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage()).build();
		return this.handleExceptionInternal(ex, wrapper, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Wrapper.builder().date(LocalDateTime.now()).title(status.getReasonPhrase()).status(status.value())
					.build();
		} else if (body instanceof String) {
			body = Wrapper.builder().date(LocalDateTime.now()).title((String) body).status(status.value()).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Wrapper.WrapperBuilder createWrapper(HttpStatus status, ProblemType problemType, String detail) {
		return Wrapper.builder().date(LocalDateTime.now()).status(status.value()).type(problemType.getUri())
				.title(problemType.getTitle()).detail(detail);
	}

}
