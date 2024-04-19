package org.karbit.ui.config;

import javax.servlet.DispatcherType;

import com.ocpsoft.pretty.PrettyFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrettyFacesConfig {

	@Bean
	public FilterRegistrationBean<PrettyFilter> prettyFilter() {
		var prettyFilter = new FilterRegistrationBean<>(new PrettyFilter());
		prettyFilter.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST,
				DispatcherType.ASYNC, DispatcherType.ERROR);
		prettyFilter.addUrlPatterns("/*");
		return prettyFilter;
	}
}
