package pu.fmi.connect4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pu.fmi.connect4.model.GameRepo;
import pu.fmi.connect4.model.GameRepoInMemory;

/**
 * Demo of Java-based Container configuration. For more details see
 * {@link https://docs.spring.io/spring-framework/reference/core/beans/java/basic-concepts.html} 
 */
@Configuration
public class AppConfig {

	@Bean
	public GameRepo gameRepo() {
		return new GameRepoInMemory();
	}
}
