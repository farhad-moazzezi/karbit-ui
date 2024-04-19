package org.karbit.ui.client.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder.Default;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.karbit.skeleton.base.result.dto.BaseResponse;
import org.karbit.ui.client.tag.exception.TagManagerServiceException;
import org.karbit.ui.client.BaseClientException;
import org.karbit.ui.config.message.ExceptionMessageConfig;
import org.karbit.user.common.dto.response.BaseUserServiceResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleManagerServiceErrorDecoder extends Default {

	private final ObjectMapper objectMapper;

	private final ExceptionMessageConfig exceptionMessageConfig;

	@Override
	public Exception decode(String s, Response response) {
		if (HttpStatus.UNPROCESSABLE_ENTITY.equals(HttpStatus.valueOf(response.status()))) {
			BaseResponse baseResponse = readResponse(response);
			return new TagManagerServiceException(
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
