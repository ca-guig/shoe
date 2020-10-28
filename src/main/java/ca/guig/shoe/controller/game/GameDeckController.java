package ca.guig.shoe.controller.game;

import ca.guig.shoe.controller.Routes;
import ca.guig.shoe.service.game.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameDeckController {

    private final GameService service;

    public GameDeckController(GameService service) {
        this.service = service;
    }

    @PostMapping(Routes.GAME_DECK_LIST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addDeckToShoe(@PathVariable String gameId, @RequestBody GameDeck deck) {
        service.addDeckToShoe(gameId, deck.getDeckId());
    }

    public static class GameDeck {
        private String deckId;

        public String getDeckId() {
            return deckId;
        }

        public void setDeckId(String deckId) {
            this.deckId = deckId;
        }
    }
}
