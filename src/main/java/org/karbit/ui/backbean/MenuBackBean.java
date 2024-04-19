package org.karbit.ui.backbean;

import java.util.Objects;

import javax.servlet.http.Cookie;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karbit.ui.util.Constant;
import org.karbit.ui.client.auth.UserManagerService;
import org.karbit.ui.config.scope.ViewScoped;
import org.karbit.ui.util.FacesContextManager;
import org.karbit.ui.util.Page;
import org.karbit.ui.util.UserStatus;
import org.karbit.user.common.dto.request.LogoutReq;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Data
@Slf4j
@Component
@ViewScoped
@RequiredArgsConstructor
public class MenuBackBean {

	private final UserManagerService userManager;

	public UserStatus getUserStatus() {
		Cookie userStatusCookie = FacesContextManager.getCookie(Constant.USER_STATUS);
		return userStatusCookie == null ? null : UserStatus.of(userStatusCookie.getValue());
	}

	public void signout() {
		final Cookie credentialCookie = FacesContextManager.getCookie(Constant.CREDENTIAL_TOKEN);
		if (Objects.nonNull(credentialCookie) && StringUtils.hasText(credentialCookie.getValue())) {
			userManager.logout(new LogoutReq(credentialCookie.getValue()));
			FacesContextManager.removeCredentialCookies();
			FacesContextManager.redirect(Page.HOME);
		}
	}
}
