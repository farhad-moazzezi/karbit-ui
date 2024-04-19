package org.karbit.ui.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.ocpsoft.pretty.faces.servlet.PrettyFacesWrappedRequest;
import lombok.SneakyThrows;
import org.apache.catalina.connector.RequestFacade;

import org.springframework.util.StringUtils;

public class FacesContextManager {

	public static void setCookie(String key, String value) {
		setCookie(key, value, null);
	}

	public static Cookie getCookie(String key) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		return (Cookie) context.getRequestCookieMap().get(key);
	}

	public static List<String> getRequestedPage() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return Arrays.stream(request.getRequestURI().split("/")).filter(StringUtils::hasText).toList();
	}

	public static void setCookie(String key, String value, Map<String, Object> cookieSettings) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.addResponseCookie(key, value, cookieSettings);
	}

	@SneakyThrows
	public static void redirect(Page page) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.redirect(context.getRequestContextPath() + page.getFileName());
	}

	public static void removeCredentialCookies() {
		removeCookie(Constant.CREDENTIAL_TOKEN);
		removeCookie(Constant.USER_STATUS);
	}

	public static void setCredentialCookies(String token, UserStatus status) {
		setCookie(Constant.CREDENTIAL_TOKEN, token);
		setCookie(Constant.USER_STATUS, status.getValue());
	}

	public static void removeCookie(String name) {
		setCookie(name, null, Map.of("maxAge", 0));
	}
}
