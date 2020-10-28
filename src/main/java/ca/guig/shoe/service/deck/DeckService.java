package ca.guig.shoe.service.deck;

import ca.guig.shoe.domain.Deck;

public interface DeckService {

    Deck createDeck(Deck deck);

    Deck readDeck(String id);

}
