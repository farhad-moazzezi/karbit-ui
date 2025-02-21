package org.karbit.ui.client.article;

import org.karbit.article.common.dto.request.CreateArticleReq;
import org.karbit.article.common.dto.request.DraftArticleReq;
import org.karbit.article.common.dto.response.ArticleDetailResp;
import org.karbit.article.common.dto.response.ArticleSummaryResp;
import org.karbit.article.common.dto.response.CreateArticleResp;
import org.karbit.ui.client.AuthenticationInterceptor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "article-manager-service", url = "${article-manager-service.url}", configuration = { AuthenticationInterceptor.class })
public interface ArticleManagerService {

	@GetMapping(value = "/summary/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	ArticleSummaryResp getArticleSummary(@PathVariable(required = false) int page);

	@GetMapping(value = "/detail/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	ArticleDetailResp getArticleDetail(@PathVariable String articleId);

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	CreateArticleResp createArticle(@RequestBody CreateArticleReq createArticleReq);

	@PostMapping(value = "/draft", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	CreateArticleResp createDraftArticle(@RequestBody DraftArticleReq draftArticleReq);
}
