package org.karbit.ui.backbean.article;

import javax.annotation.PostConstruct;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karbit.postmng.common.dto.response.ArticleDetailResp;
import org.karbit.ui.client.article.ArticleManagerService;
import org.karbit.ui.config.scope.ViewScoped;

import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
@ViewScoped
@RequiredArgsConstructor
public class ArticleLoader {

	private final ArticleManagerService service;

	private String articleId;

	private ArticleDetailResp articleDetail;

	public void loadArticle() {
		setArticleDetail(service.getArticleDetail(articleId));
	}
}
