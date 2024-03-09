package pu.fmi.connect4;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import pu.fmi.connect4.model.Game;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class Connect4ApplicationTests {

	@Test
	void testListGamesRestEndpoit(@Autowired WebTestClient client) {
		client
			.post()
			.uri("/games")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus()
			.isOk();

		client
			.get()
			.uri("/games")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus()
			.isOk()
			.expectBodyList(Game.class)
			.hasSize(1);
	}

}
