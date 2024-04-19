package org.karbit.ui.client;

import java.util.Objects;

import javax.servlet.http.Cookie;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.karbit.ui.util.Constant;
import org.karbit.ui.util.FacesContextManager;

public class AuthenticationInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		Cookie tokenCookie = FacesContextManager.getCookie(Constant.CREDENTIAL_TOKEN);
		if (Objects.nonNull(tokenCookie)) {
			requestTemplate.header("TOKEN", tokenCookie.getValue());
		}
	}
}
