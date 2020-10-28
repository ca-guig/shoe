package ca.guig.shoe.repository.game;

import ca.guig.shoe.domain.Game;
import ca.guig.shoe.repository.AbstractInMemoryCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryGameRepository extends AbstractInMemoryCrudRepository<Game> implements GameRepository {}
