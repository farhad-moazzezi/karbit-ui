package org.karbit.ui.backbean.credential;

import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.servlet.http.Cookie;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karbit.skeleton.constant.Agent;
import org.karbit.ui.util.Constant;
import org.karbit.ui.client.BaseClientException;
import org.karbit.ui.client.auth.UserManagerService;
import org.karbit.ui.config.CookieConfig;
import org.karbit.ui.config.scope.ViewScoped;
import org.karbit.ui.util.FacesContextManager;
import org.karbit.ui.util.GrowlHandler;
import org.karbit.ui.util.Page;
import org.karbit.ui.util.UserStatus;
import org.karbit.user.common.dto.request.PhoneNumberBaseLoginReq;
import org.karbit.user.common.dto.request.PhoneNumberBaseSignupReq;
import org.karbit.user.common.dto.response.AuthResponse;
import org.karbit.user.common.dto.response.LoginResp;
import org.karbit.user.common.dto.response.SignupResp;

import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
@ViewScoped
@RequiredArgsConstructor
public class CredentialManager {

	private final UserManagerService userManager;

	private final CookieConfig cookieConfig;

	private static final Set<Page> unprotectedPages = Set.of(Page.SIGNIN, Page.SIGNUP);

	private String cellNumber;

	private String nickname;

	@PostConstruct
	public void checkCredentialState() {
		final Cookie credentialCookie = FacesContextManager.getCookie(Constant.CREDENTIAL_TOKEN);
		if (Objects.isNull(credentialCookie) && isRequestUnprotectedPage()) {
			return;
		} try {
			AuthResponse authenticate = userManager.authenticate(credentialCookie.getValue(), Agent.WEB);
			switch (authenticate.getStatus()) {
				case "ACTIVE" -> handleActiveUser(authenticate);
				case "READY_TO_ACTIVATION" -> handleReadyToActivationUsers(authenticate);
				default -> handleNotActiveUsers();
			}
		} catch (Exception exception) {
			handleNotActiveUsers();
		}
	}

	private void handleActiveUser(AuthResponse authenticate) {
		FacesContextManager.setCredentialCookies(authenticate.getToken(), UserStatus.of(authenticate.getStatus()));
		FacesContextManager.redirect(Page.HOME);
	}

	private void handleReadyToActivationUsers(AuthResponse authenticate) {
		FacesContextManager.setCredentialCookies(authenticate.getToken(), UserStatus.of(authenticate.getStatus()));
		FacesContextManager.redirect(Page.USER_ACTIVATION);
	}

	private void handleNotActiveUsers() {
		FacesContextManager.removeCookie(Constant.CREDENTIAL_TOKEN); FacesContextManager.redirect(Page.SIGNIN);
	}

	private boolean isRequestUnprotectedPage() {
		return unprotectedPages.stream().anyMatch(page -> page.getFileName().equals(FacesContextManager.getRequestedPage().get(0)));
	}

	public void signin() {
		log.info("going to login by cell number -> cell number: {}", cellNumber); try {
			LoginResp loginResponse = userManager.login(new PhoneNumberBaseLoginReq(getCellNumber()));
			FacesContextManager.setCookie(Constant.CREDENTIAL_TOKEN, loginResponse.getToken());
			FacesContextManager.setCookie(Constant.USER_ID, loginResponse.getUserId());
			FacesContextManager.setCookie(Constant.USER_STATUS, "READY_TO_ACTIVATION");
			FacesContextManager.redirect(Page.USER_ACTIVATION);
		} catch (BaseClientException exception) {
			GrowlHandler.showMessage(exception.getTitle(), exception.getDetail(), FacesMessage.SEVERITY_ERROR);
		}
	}

	public void signup() {
		log.info("going to login by cell number -> cell number: {}", cellNumber); try {
			PhoneNumberBaseSignupReq signupReq = new PhoneNumberBaseSignupReq(); signupReq.setPhoneNumber(cellNumber);
			signupReq.setNickname(nickname); SignupResp signupResp = userManager.signup(signupReq);
			FacesContextManager.setCookie(Constant.CREDENTIAL_TOKEN, signupResp.getToken());
			FacesContextManager.setCookie(Constant.USER_STATUS, "READY_TO_ACTIVATION");
			FacesContextManager.redirect(Page.USER_ACTIVATION);
		} catch (BaseClientException exception) {
			GrowlHandler.showMessage(exception.getTitle(), exception.getDetail(), FacesMessage.SEVERITY_ERROR);
		}
	}
}

