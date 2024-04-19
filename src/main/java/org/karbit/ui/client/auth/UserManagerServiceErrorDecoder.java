package org.karbit.ui.client.auth;

import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder.Default;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.karbit.skeleton.base.result.dto.BaseResponse;
import org.karbit.ui.client.BaseClientException;
import org.karbit.ui.config.message.ExceptionMessageConfig;
import org.karbit.user.common.dto.response.BaseUserServiceResponse;
import org.karbit.ui.client.auth.exception.UserManagerServiceException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
@RequiredArgsConstructor
public class UserManagerServiceErrorDecoder extends Default {

	private final ObjectMapper objectMapper;

	private final ExceptionMessageConfig exceptionMessageConfig;

	private final Set<HttpStatus> PROCESSABLE_STATUS = Set.of(UNPROCESSABLE_ENTITY, BAD_REQUEST);

	@Override
	public Exception decode(String s, Response response) {
		if (PROCESSABLE_STATUS.contains(HttpStatus.valueOf(response.status()))) {
			BaseResponse baseResponse = readResponse(response);
			return new UserManagerServiceException(
					exceptionMessageConfig.getClient().getDefaultFailureTitle(),
					baseResponse.getResult().getMessage()
			);
		}
		return new BaseClientException(
				exceptionMessageConfig.getClient().getDefaultFailureTitle(),
				exceptionMessageConfig.getClient().getDefaultFailureDetail()
		);
	}

	@SneakyThrows
	private BaseResponse readResponse(Response response) {
		return objectMapper.readValue(response.body().asInputStream(), BaseUserServiceResponse.class);
	}
}
