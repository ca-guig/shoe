package ca.guig.shoe.service.deck;

import static org.assertj.core.api.Assertions.assertThat;

import ca.guig.shoe.domain.Card;
import ca.guig.shoe.domain.Deck;
import ca.guig.shoe.domain.DeckCard;
import ca.guig.shoe.repository.deck.DeckRepository;
import ca.guig.shoe.repository.deck.InMemoryDeckRepository;
import ca.guig.shoe.service.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class DefaultDeckServiceTest {

    @InjectMocks
    private DefaultDeckService deckService;

    @Spy
    private final DeckRepository deckRepository = new InMemoryDeckRepository();

    @Spy
    private IdGenerator idGenerator = new IdGenerator() {
        private int index = 1000;

        @Override
        public String generateId() {
            return "" + index++;
        }
    };

    @Test
    void createShouldSaveDeckWithNewIdAndCards() {
        Deck deck = deckService.createDeck(deck("red"));

        assertThat(deck)
                .hasFieldOrPropertyWithValue("id", "1000")
                .hasFieldOrPropertyWithValue("color", "red");
        assertThat(deck.getCards()).hasSize(52);
        assertThat(deck.getCards().get(0))
                .hasFieldOrPropertyWithValue("id", "1001")
                .hasFieldOrPropertyWithValue("value", Card.ACE_OF_HEARTS);
        assertThat(deck.getCards().get(51))
                .hasFieldOrPropertyWithValue("id", "1052")
                .hasFieldOrPropertyWithValue("value", Card.KING_OF_DIAMONDS);
    }

    @Test
    void readShouldReturnDeckWhenDeckExists() {
        given(deck("blue"));

        Deck deck = deckService.readDeck("1000");

        assertThat(deck)
                .hasFieldOrPropertyWithValue("id", "1000")
                .hasFieldOrPropertyWithValue("color", "blue");
        assertThat(deck.getCards()).hasSize(52);
        assertThat(deck.getCards().get(0))
                .hasFieldOrPropertyWithValue("id", "1001")
                .hasFieldOrPropertyWithValue("value", Card.ACE_OF_HEARTS);
        assertThat(deck.getCards().get(51))
                .hasFieldOrPropertyWithValue("id", "1052")
                .hasFieldOrPropertyWithValue("value", Card.KING_OF_DIAMONDS);
    }

    @Test
    void readShouldThrowExceptionWhenDeckDoesNotExist() {
        Assertions.assertThatThrownBy(() -> deckService.readDeck("9999")).isInstanceOf(DeckNotFoundException.class);
    }

    private void given(Deck deck) {
        deckService.createDeck(deck);
    }

    private static Deck deck(String color) {
        return Deck.builder().withColor(color).build();
    }

    private static Deck deck(String id, String color, List<DeckCard> cards) {
        return Deck.builder().withId(id).withColor(color).withCards(cards).build();
    }
}
