package org.karbit.ui.config.message;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("exception")
public class ExceptionMessageConfig {

	private Client client;

	@Getter
	@Setter
	public static class Client {
		private String defaultFailureTitle;

		private String defaultFailureDetail;
	}
}
