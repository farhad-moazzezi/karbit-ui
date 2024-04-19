package org.karbit.ui.util;

import lombok.Getter;

public enum Page {

	USER_ACTIVATION("activation.xhtml"),
	SIGNUP("signup.xhtml"),
	HOME("index.xhtml"),
	SIGNIN("signin.xhtml");

	@Getter
	private final String fileName;

	Page(String fileName) {
		this.fileName = fileName;
	}
}
