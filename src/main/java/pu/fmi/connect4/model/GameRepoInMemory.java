package pu.fmi.connect4.model;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class GameRepoInMemory implements GameRepo {

	private final Logger log = LoggerFactory.getLogger(GameRepoInMemory.class);

	private Map<UUID, Game> games = new ConcurrentHashMap<>();

	public GameRepoInMemory() {
		log.info("GameRepoInMemory constructor");
	}

	@Override
	public void save(Game game) {
		games.put(game.getGameId(), game);
	}

	@Override
	public Game get(UUID gameId) {
		return games.get(gameId);
	}

	@Override
	public void delete(Game game) {
		games.remove(game.getGameId());
	}

	@Override
	public Collection<Game> listAll() {
		return games.values();
	}

	/**
	 * Demo of lifecicle callback when injection is done to allow additional
	 * initialization. For more details see
	 * {@link https://docs.spring.io/spring-framework/reference/core/beans/java/bean-annotation.html#beans-java-lifecycle-callbacks}
	 */
	@PostConstruct
	public void init() {
		log.info("GameRepoInMemory post construct");
	}

	/**
	 * Demo of lifecicle callback when been is to be removed. For more details see
	 * {@link https://docs.spring.io/spring-framework/reference/core/beans/java/bean-annotation.html#beans-java-lifecycle-callbacks}
	 */
	@PreDestroy
	public void destroy() {
		log.info("GameRepoInMemory pre destroy");
		games.clear();
	}
}
