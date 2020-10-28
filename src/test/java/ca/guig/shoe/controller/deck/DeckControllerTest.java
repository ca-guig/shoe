package ca.guig.shoe.controller.deck;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.guig.shoe.controller.Routes;
import ca.guig.shoe.domain.Card;
import ca.guig.shoe.domain.Deck;
import ca.guig.shoe.domain.DeckCard;
import ca.guig.shoe.service.deck.DeckNotFoundException;
import ca.guig.shoe.service.deck.DeckService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(controllers = DeckController.class)
public class DeckControllerTest {

    @MockBean
    private DeckService deckService;

    @Autowired
    private MockMvc mvc;

    @Test
    void createShouldCreateDeck() throws Exception {
        BDDMockito
                .willReturn(deck("1000", "mock", card("99", Card.EIGHT_OF_CLUBS)))
                .given(deckService)
                .createDeck(BDDMockito.any());

        String json = "{\"color\":\"red\"}";
        mvc
                .perform(post(Routes.DECK_LIST).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/rest/v1/decks/1000"));

        BDDMockito.verify(deckService).createDeck(BDDMockito.eq(deck("red")));
    }

    @Test
    void readShouldReturnDeck() throws Exception {
        BDDMockito
                .willReturn(deck("1000", "mock", card("99", Card.EIGHT_OF_CLUBS)))
                .given(deckService)
                .readDeck("1000");

        String expectedJson =
                "{\"id\":\"1000\",\"color\":\"mock\",\"cards\":[{\"id\":\"99\",\"value\":\"EIGHT_OF_CLUBS\"}]}";
        mvc
                .perform(get(Routes.DECK, "1000"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, true));
    }

    @Test
    void shouldHandleExceptionWhenDeckIsNotFound() throws Exception {
        BDDMockito.willThrow(new DeckNotFoundException()).given(deckService).readDeck("1000");

        mvc
                .perform(get(Routes.DECK, "1000"))
                .andExpect(status().isNotFound());
    }

    private static Deck deck(String color) {
        return Deck.builder().withColor(color).build();
    }

    private static Deck deck(String id, String color, DeckCard card) {
        return Deck.builder().withId(id).withColor(color).withCards(List.of(card)).build();
    }

    private static DeckCard card(String id, Card value) {
        return DeckCard.builder().withId(id).withValue(value).build();
    }
}
