package ca.guig.shoe.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void gameCrudTest() {
        URI uri = restTemplate.postForLocation("/rest/v1/games", new TestGame("test-game-name"));

        TestGame savedGame = restTemplate.getForObject(uri, TestGame.class);
        assertThat(savedGame).hasFieldOrPropertyWithValue("name", "test-game-name");

        restTemplate.put(uri, new TestGame("new-test-game-name"));

        TestGame updatedGame = restTemplate.getForObject(uri, TestGame.class);
        assertThat(updatedGame).hasFieldOrPropertyWithValue("name", "new-test-game-name");

        restTemplate.delete(uri);
    }

    public static class TestGame {
        private String id;

        private String name;

        public TestGame(String name) {
            this.name = name;
        }

        public TestGame(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
