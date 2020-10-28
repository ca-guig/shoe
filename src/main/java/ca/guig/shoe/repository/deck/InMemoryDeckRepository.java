package ca.guig.shoe.repository.deck;

import ca.guig.shoe.domain.Deck;
import ca.guig.shoe.repository.AbstractInMemoryCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryDeckRepository extends AbstractInMemoryCrudRepository<Deck> implements DeckRepository {}
