package org.karbit.ui.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseClientException extends RuntimeException {
	private final String title;
	private final String detail;
}