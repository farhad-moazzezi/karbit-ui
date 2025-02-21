package org.karbit.ui.backbean.article;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karbit.article.common.dto.response.ArticleDetailResp;
import org.karbit.ui.client.article.ArticleManagerService;
import org.karbit.ui.config.scope.ViewScoped;

import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
@ViewScoped
@RequiredArgsConstructor
public class ReadArticleBean {
	private final ArticleManagerService service;

	private String articleId;

	private ArticleDetailResp articleDetail;

	public void loadArticle() {
		setArticleDetail(service.getArticleDetail(articleId));
	}

	public void unlike() {
		return;
	}

	public void like() {
		return;
	}

	public void mark() {
		return;
	}

	public void unmark() {
		return;
	}
}
