package org.karbit.ui.backbean.credential;

import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karbit.skeleton.constant.Agent;
import org.karbit.ui.util.Constant;
import org.karbit.ui.config.CookieConfig;
import org.karbit.ui.util.FacesContextManager;
import org.karbit.ui.client.auth.UserManagerService;
import org.karbit.ui.config.scope.ViewScoped;
import org.karbit.ui.util.Page;
import org.karbit.ui.util.UserStatus;
import org.karbit.user.common.dto.request.LogoutReq;
import org.karbit.user.common.dto.request.UserActivateReq;
import org.karbit.user.common.dto.response.AuthResponse;
import org.karbit.user.common.dto.response.UserActivateResp;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Data
@Slf4j
@Component
@ViewScoped
@RequiredArgsConstructor
public class ActivationBackBean {

	private final UserManagerService userManager;

	private final CookieConfig cookieConfig;

	private String otp;

	@PostConstruct
	public void checkCredentialState() {
		final Cookie credentialCookie = FacesContextManager.getCookie(Constant.CREDENTIAL_TOKEN);
		if (Objects.isNull(credentialCookie) || Boolean.FALSE.equals(StringUtils.hasText(credentialCookie.getValue()))) {
			FacesContextManager.redirect(Page.SIGNIN);
			return;
		}
		try {
			AuthResponse authenticate = userManager.authenticate(credentialCookie.getValue(), Agent.WEB);
			if (Boolean.FALSE.equals(authenticate.getStatus().equals("READY_TO_ACTIVATION"))) {
				FacesContextManager.redirect(Page.SIGNIN);
			}
		} catch (Exception exception) {
			FacesContextManager.removeCookie(Constant.CREDENTIAL_TOKEN);
			FacesContextManager.redirect(Page.SIGNIN);
		}

	}

	public void activate() {
		final Cookie credentialCookie = FacesContextManager.getCookie(Constant.CREDENTIAL_TOKEN);
		if (Objects.nonNull(credentialCookie) && StringUtils.hasText(credentialCookie.getValue())) {
			UserActivateResp activateResp = userManager.activateUser(new UserActivateReq(otp), credentialCookie.getValue());
			FacesContextManager.setCookie(Constant.CREDENTIAL_TOKEN, activateResp.getToken(), Map.of("maxAge", cookieConfig.getSession().getMaxAge()));
			FacesContextManager.setCookie(Constant.USER_STATUS, UserStatus.ACTIVE.getValue(), Map.of("maxAge", cookieConfig.getSession().getMaxAge()));
			FacesContextManager.redirect(Page.HOME);
		}
	}

	public void signinWithAnotherNumber() {
		final Cookie credentialCookie = FacesContextManager.getCookie(Constant.CREDENTIAL_TOKEN);
		if (Objects.nonNull(credentialCookie) && StringUtils.hasText(credentialCookie.getValue())) {
			userManager.logout(new LogoutReq(credentialCookie.getValue()));
			FacesContextManager.removeCredentialCookies();
			FacesContextManager.redirect(Page.SIGNIN);
		}
	}
}
