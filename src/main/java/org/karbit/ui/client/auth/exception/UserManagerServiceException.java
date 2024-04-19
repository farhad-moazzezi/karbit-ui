package org.karbit.ui.client.auth.exception;

import org.karbit.ui.client.BaseClientException;

public class UserManagerServiceException extends BaseClientException {
	public UserManagerServiceException(String title, String detail) {
		super(title, detail);
	}
}
