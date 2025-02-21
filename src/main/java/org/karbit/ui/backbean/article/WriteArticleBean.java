package org.karbit.ui.backbean.article;

import java.util.List;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karbit.article.common.dto.request.DraftArticleReq;
import org.karbit.article.common.dto.response.CreateArticleResp;
import org.karbit.tagmng.common.dto.common.TagInfo;
import org.karbit.tagmng.common.dto.request.SearchTagReq;
import org.karbit.tagmng.common.dto.response.FoundTagResp;
import org.karbit.ui.client.BaseClientException;
import org.karbit.ui.client.article.ArticleManagerService;
import org.karbit.ui.client.tag.TagManagerService;
import org.karbit.ui.config.scope.ViewScoped;
import org.karbit.ui.util.GrowlHandler;

import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
@ViewScoped
@RequiredArgsConstructor
public class WriteArticleBean {
	private String articleId;

	private String title;

	private String content;

	private List<String> selectedTags;

	private final TagManagerService tagService;

	private final ArticleManagerService articleService;

	public void showPreview() {
		return;
	}

	public List<TagInfo> findCorrespondingTags(String searchedValue) {
		var tagReq = new SearchTagReq();
		tagReq.setValue(searchedValue);
		FoundTagResp tags = tagService.searchTag(tagReq);
		return tags.getTags();
	}

	public void autoDraft() {
		try {
			CreateArticleResp draftArticle = upsertDraftArticle();
			setArticleId(draftArticle.getArticleId());
		} catch (BaseClientException exception) {
			GrowlHandler.showMessage(exception.getTitle(), exception.getDetail(), FacesMessage.SEVERITY_ERROR);
		}
	}

	private CreateArticleResp upsertDraftArticle() {
		DraftArticleReq req = new DraftArticleReq();
		req.setContent(getContent());
		req.setTitle(getTitle());
		req.setArticleId(getArticleId());
		req.setTagLabels(getSelectedTags().stream().map(String::trim).collect(Collectors.toSet()));
		return articleService.createDraftArticle(req);
	}
}
