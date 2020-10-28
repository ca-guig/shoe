package ca.guig.shoe.service.deck;

import static java.util.stream.Collectors.toList;

import ca.guig.shoe.domain.Card;
import ca.guig.shoe.domain.Deck;
import ca.guig.shoe.domain.DeckCard;
import ca.guig.shoe.repository.deck.DeckRepository;
import ca.guig.shoe.service.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DefaultDeckService implements DeckService {

    private final DeckRepository repository;

    private final IdGenerator idGenerator;

    public DefaultDeckService(DeckRepository repository, IdGenerator idGenerator) {
        this.repository = repository;
        this.idGenerator = idGenerator;
    }

    @Override
    public Deck createDeck(Deck deck) {
        Deck copiedDeck = Deck.builder().withId(idGenerator.generateId())
                .withColor(deck.getColor())
                .withCards(buildNewCardSet())
                .build();
        return repository.save(copiedDeck);
    }

    @Override
    public Deck readDeck(String id) {
        Deck deck = repository.read(id);
        if (deck == null) {
            throw new DeckNotFoundException();
        }
        return deck;
    }

    private List<DeckCard> buildNewCardSet() {
        return Arrays
                .stream(Card.values())
                .map((card) -> DeckCard.builder().withId(idGenerator.generateId()).withValue(card).build())
                .collect(toList());
    }
}
