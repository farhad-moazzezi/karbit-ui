package org.karbit.ui.client.auth;

import org.karbit.skeleton.constant.Header;
import org.karbit.user.common.dto.request.LogoutReq;
import org.karbit.user.common.dto.request.PhoneNumberBaseLoginReq;
import org.karbit.user.common.dto.request.PhoneNumberBaseSignupReq;
import org.karbit.user.common.dto.request.UserActivateReq;
import org.karbit.user.common.dto.response.AuthResponse;
import org.karbit.user.common.dto.response.LoginResp;
import org.karbit.user.common.dto.response.LogoutResp;
import org.karbit.user.common.dto.response.SignupResp;
import org.karbit.user.common.dto.response.UserActivateResp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-manager-service", url = "${user-manager-service.url}")
public interface UserManagerService {

	@PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
	AuthResponse authenticate(@RequestHeader(name = Header.TOKEN) String token, @RequestHeader(name = Header.AGENT) String Agent);

	@PostMapping(value = "/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	LoginResp login(PhoneNumberBaseLoginReq loginReq);

	@PostMapping(value = "/auth/activate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	UserActivateResp activateUser(UserActivateReq userActivateReq, @RequestHeader String token);

	@PostMapping(value = "/auth/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	SignupResp signup(@RequestBody PhoneNumberBaseSignupReq signupReq);

	@PostMapping(value = "/auth/logout", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	LogoutResp logout(@RequestBody LogoutReq logoutReq);
}
