package pu.fmi.connect4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
