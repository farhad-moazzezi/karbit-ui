package org.karbit.ui.client.tag.exception;

import org.karbit.ui.client.BaseClientException;

public class TagManagerServiceException extends BaseClientException {
	public TagManagerServiceException(String title, String detail) {
		super(title, detail);
	}
}
