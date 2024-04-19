package org.karbit.ui.backbean.article;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karbit.postmng.common.dto.common.ArticleSummaryDto;
import org.karbit.postmng.common.dto.response.ArticleSummaryResp;
import org.karbit.ui.client.article.ArticleManagerService;
import org.karbit.ui.config.scope.ViewScoped;

import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
@ViewScoped
@RequiredArgsConstructor
public class ArticleSearcher {

	private final ArticleManagerService service;

	private int page = 0;

	public List<ArticleSummaryDto> getArticleSummary() {
		ArticleSummaryResp articleSummary = service.getArticleSummary(page);
		return articleSummary.getArticles();
	}
}
