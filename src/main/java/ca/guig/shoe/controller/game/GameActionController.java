package ca.guig.shoe.controller.game;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import ca.guig.shoe.controller.Routes;
import ca.guig.shoe.service.game.GameService;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameActionController {

    private static final Logger logger = getLogger(lookup().lookupClass());

    private final GameService gameService;

    public GameActionController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping(Routes.GAME_ACTION_LIST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createAction(@PathVariable String gameId, @RequestBody GameAction action) {
        if (action instanceof ShuffleShoeGameAction) {
            gameService.shuffleShoe(gameId);
        } else if (action instanceof DealCardsGameAction) {
            DealCardsGameAction dealCardsAction = (DealCardsGameAction) action;
            gameService.dealCards(gameId, dealCardsAction.getPlayerId(), dealCardsAction.getNumberOfCards());
        } else {
            throw new RuntimeException("Unknown action type");
        }
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = ShuffleShoeGameAction.class, name = "SHUFFLE_SHOE"),
            @JsonSubTypes.Type(value = DealCardsGameAction.class, name = "DEAL_CARDS")})
    public abstract static class GameAction {}

    public static class ShuffleShoeGameAction extends GameAction {}

    public static class DealCardsGameAction extends GameAction {

        private String playerId;

        private int numberOfCards;

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public int getNumberOfCards() {
            return numberOfCards;
        }

        public void setNumberOfCards(int numberOfCards) {
            this.numberOfCards = numberOfCards;
        }
    }
}
