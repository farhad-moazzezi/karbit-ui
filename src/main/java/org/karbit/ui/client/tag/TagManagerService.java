package org.karbit.ui.client.tag;

import org.karbit.tagmng.common.dto.request.SearchTagReq;
import org.karbit.tagmng.common.dto.response.FoundTagResp;
import org.karbit.ui.client.AuthenticationInterceptor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "tag-manager-service", url = "${tag-manager-service.url}", configuration = { AuthenticationInterceptor.class })
public interface TagManagerService {

	@PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public FoundTagResp searchTag(@RequestBody SearchTagReq searchTagReq);
}
