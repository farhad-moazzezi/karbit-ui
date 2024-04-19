package org.karbit.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;

@EnableFeignClients
@SpringBootApplication
@ConfigurationPropertiesScan
@PropertySource("classpath:message.properties")
public class UserInterfaceBootstrap {
	public static void main(String[] args) {
		SpringApplication.run(UserInterfaceBootstrap.class, args);
	}
}
