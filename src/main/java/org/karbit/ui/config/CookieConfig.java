package org.karbit.ui.config;

import java.util.concurrent.TimeUnit;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cookie")
public class CookieConfig {

	private Session session;

	@Getter
	@Setter
	public static class Session {
		private int maxAge;

		public int getMaxAge() {
			return (int) TimeUnit.DAYS.toSeconds(maxAge);
		}
	}
}
