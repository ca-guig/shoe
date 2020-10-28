package ca.guig.shoe.utils;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import ca.guig.shoe.domain.Card;
import ca.guig.shoe.domain.DeckCard;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

class CardShufflerTest {

    private static final Logger logger = getLogger(lookup().lookupClass());

    @Test
    void shuffleShouldShuffleDeck() {
        List<DeckCard> cards = Arrays
                .stream(Card.values())
                .map((card) -> DeckCard.builder().withId("mock").withValue(card).build())
                .collect(toList());

        CardShuffler shuffler = new CardShuffler(cards);
        shuffler.shuffle();

        List<DeckCard> shuffledCards = shuffler.getCards();
        assertThat(shuffledCards)
                .hasSize(cards.size())
                .isNotEqualTo(cards);
    }
}
