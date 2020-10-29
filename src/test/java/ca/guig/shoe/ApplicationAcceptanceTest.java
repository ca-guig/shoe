package ca.guig.shoe;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import ca.guig.shoe.controller.Routes;
import ca.guig.shoe.domain.Card;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationAcceptanceTest {

    private static final Logger logger = getLogger(lookup().lookupClass());

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void typicalScenario() throws JsonProcessingException {
        // Create Red Deck
        URI redDeckUri = restTemplate.postForLocation(Routes.DECK_LIST, new TestDeck().color("red"));
        String redDeckId = restTemplate.getForObject(redDeckUri, TestDeck.class).getId();

        // Create Blue Deck
        URI blueDeckUri = restTemplate.postForLocation(Routes.DECK_LIST, new TestDeck().color("blue"));
        String blueDeckId = restTemplate.getForObject(blueDeckUri, TestDeck.class).getId();

        // Create Test Game
        URI testGameUri = restTemplate.postForLocation(Routes.GAME_LIST, new TestGame().name("test"));
        logger.info("game uri = {}", testGameUri);
        String testGameId = restTemplate.getForObject(testGameUri, TestGame.class).getId();

        // Add decks to the game
        restTemplate.postForEntity(Routes.GAME_DECK_LIST, Map.of("deckId", redDeckId), Void.class, testGameId);
        restTemplate.postForEntity(Routes.GAME_DECK_LIST, Map.of("deckId", blueDeckId), Void.class, testGameId);
        assertThat(restTemplate.getForObject(Routes.GAME, TestGame.class, testGameId).shoe.cards).hasSize(104);

        // Shuffle game shoe
        ResponseEntity<Void> shuffleResponse = restTemplate.postForEntity(
                Routes.GAME_ACTION_LIST,
                Map.of("type", "SHUFFLE_SHOE"),
                Void.class,
                testGameId);
        assertThat(shuffleResponse).hasFieldOrPropertyWithValue("statusCode", HttpStatus.CREATED);

        // Add player Alice and Bobby
        restTemplate.postForEntity(Routes.GAME_PLAYER_LIST, new TestPlayer().name("Alice"), Void.class, testGameId);
        restTemplate.postForEntity(Routes.GAME_PLAYER_LIST, new TestPlayer().name("Bobby"), Void.class, testGameId);
        List<TestPlayer> players = restTemplate.getForObject(Routes.GAME, TestGame.class, testGameId).players;
        assertThat(players)
                .hasSize(2)
                .extracting("name")
                .containsExactlyInAnyOrder("Alice", "Bobby");

        String aliceId = players.stream()
                .filter(player -> player.name.equals("Alice"))
                .map(TestPlayer::getId).findFirst().orElse(null);
        String bobbyId = players.stream()
                .filter(player -> player.name.equals("Bobby"))
                .map(TestPlayer::getId).findFirst().orElse(null);

        // Deal 5 cards to each players
        restTemplate.postForEntity(
                Routes.GAME_ACTION_LIST,
                Map.of("type", "DEAL_CARDS", "playerId", aliceId, "numberOfCards", 5),
                Void.class,
                testGameId);
        restTemplate.postForEntity(
                Routes.GAME_ACTION_LIST,
                Map.of("type", "DEAL_CARDS", "playerId", bobbyId, "numberOfCards", 5),
                Void.class,
                testGameId);

        TestGame game = restTemplate.getForObject(Routes.GAME, TestGame.class, testGameId);
        assertThat(game.shoe.cards).hasSize(94);
        assertThat(game.players.get(0).getHand().getCards()).hasSize(5);
        assertThat(game.players.get(1).getHand().getCards()).hasSize(5);
        assertThat(game.players.get(0).getHand().getValue())
                .isGreaterThanOrEqualTo(game.players.get(1).getHand().getValue());

        logger.info("Game: {}", new ObjectMapper().writeValueAsString(game));
    }

    public static class TestDeck {
        private String id;
        private String color;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public TestDeck id(final String id) {
            this.id = id;
            return this;
        }

        public TestDeck color(final String color) {
            this.color = color;
            return this;
        }
    }

    public static class TestGame {
        private String id;
        private String name;
        private TestShoe shoe;
        private List<TestPlayer> players;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public TestShoe getShoe() {
            return shoe;
        }

        public void setShoe(TestShoe shoe) {
            this.shoe = shoe;
        }

        public List<TestPlayer> getPlayers() {
            return players;
        }

        public void setPlayers(List<TestPlayer> players) {
            this.players = players;
        }

        public TestGame id(final String id) {
            this.id = id;
            return this;
        }

        public TestGame name(final String name) {
            this.name = name;
            return this;
        }

        public TestGame shoe(final TestShoe shoe) {
            this.shoe = shoe;
            return this;
        }

        public TestGame players(final List<TestPlayer> players) {
            this.players = players;
            return this;
        }
    }

    public static class TestShoe {
        private List<TestCard> cards;

        public List<TestCard> getCards() {
            return cards;
        }

        public void setCards(List<TestCard> cards) {
            this.cards = cards;
        }

        public TestShoe cards(final List<TestCard> cards) {
            this.cards = cards;
            return this;
        }
    }

    public static class TestPlayer {
        private String id;
        private String name;
        private TestHand hand;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public TestHand getHand() {
            return hand;
        }

        public void setHand(TestHand hand) {
            this.hand = hand;
        }

        public TestPlayer id(final String id) {
            this.id = id;
            return this;
        }

        public TestPlayer name(final String name) {
            this.name = name;
            return this;
        }

        public TestPlayer hand(final TestHand hand) {
            this.hand = hand;
            return this;
        }
    }

    public static class TestHand {
        private int value;
        private List<TestCard> cards;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public List<TestCard> getCards() {
            return cards;
        }

        public void setCards(List<TestCard> cards) {
            this.cards = cards;
        }

        public TestHand value(final int value) {
            this.value = value;
            return this;
        }

        public TestHand cards(final List<TestCard> cards) {
            this.cards = cards;
            return this;
        }
    }

    public static class TestCard {
        private Card value;

        public Card getValue() {
            return value;
        }

        public void setValue(Card value) {
            this.value = value;
        }

        public TestCard value(final Card value) {
            this.value = value;
            return this;
        }
    }
}
