package org.karbit.ui.client.article.exception;

import org.karbit.ui.client.BaseClientException;

public class ArticleManagerServiceException extends BaseClientException {
	public ArticleManagerServiceException(String title, String detail) {
		super(title, detail);
	}
}
