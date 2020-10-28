package ca.guig.shoe.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import ca.guig.shoe.domain.Card;
import ca.guig.shoe.domain.DeckCard;
import ca.guig.shoe.domain.Player;
import ca.guig.shoe.domain.PlayerHand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class PlayerSorterTest {

    @Test
    void sortPlayerByHandValueFromHighestToLowestValue() {
        List<Player> players = new ArrayList<>();
        players.add(player("Alice", Card.TEN_OF_DIAMONDS, Card.KING_OF_HEARTS));
        players.add(player("Bobby", Card.THREE_OF_DIAMONDS, Card.ACE_OF_SPADES));
        players.add(player("Carol", Card.QUEEN_OF_CLUBS, Card.QUEEN_OF_HEARTS));

        players.sort(new PlayerSorter());

        assertThat(players)
                .extracting("name", "hand.value")
                .containsExactly(tuple("Carol", 24), tuple("Alice", 23), tuple("Bobby", 4));
    }

    private static Player player(String name, Card... cards) {
        List<DeckCard> deckCards = Arrays
                .stream(cards)
                .map(card -> DeckCard.builder().withId("mock").withValue(card).build())
                .collect(Collectors.toList());

        return Player.builder()
                .withName(name)
                .withHand(PlayerHand.builder().withCards(deckCards).build())
                .build();
    }
}