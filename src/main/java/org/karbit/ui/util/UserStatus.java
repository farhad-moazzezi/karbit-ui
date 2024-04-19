package org.karbit.ui.util;

import java.util.Arrays;

public enum UserStatus {
	ACTIVE("ACTIVE"),
	READY_TO_ACTIVATION("READY_TO_ACTIVATION");

	private final String value;

	UserStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static UserStatus of(String value) {
		return Arrays.stream(UserStatus.values()).filter(status -> status.getValue().equals(value)).findFirst().orElse(null);
	}
}
