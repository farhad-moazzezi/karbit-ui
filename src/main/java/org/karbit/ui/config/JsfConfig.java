package org.karbit.ui.config;

import java.util.Collections;
import java.util.Set;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsfConfig {

	@Bean
	ServletRegistrationBean<FacesServlet> jsfServletRegistration(ServletContext servletContext) {
		//spring boot only works if this is set
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());

		//additional config:
		servletContext.setInitParameter("primefaces.THEME", "none");
		servletContext.setSessionTrackingModes(Set.of(SessionTrackingMode.COOKIE));

		//registration
		ServletRegistrationBean<FacesServlet> srb = new ServletRegistrationBean<>();
		srb.setServlet(new FacesServlet());
		srb.setUrlMappings(Collections.singletonList("*.xhtml"));
		srb.setLoadOnStartup(Integer.MAX_VALUE);
		return srb;
	}
}
