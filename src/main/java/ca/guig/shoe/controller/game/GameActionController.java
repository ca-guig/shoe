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
public class GameActionController {

    private final GameService gameService;

    public GameActionController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping(Routes.GAME_ACTION_LIST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createAction(@PathVariable String gameId, @RequestBody GameAction action) {
        if (action.getType() == GameActionType.SHUFFLE_SHOE) {
            gameService.shuffleShoe(gameId);
        } else {
            throw new RuntimeException("Unknown action type");
        }
    }

    public enum GameActionType {
        SHUFFLE_SHOE
    }

    public static class GameAction {
        private GameActionType type;

        public GameActionType getType() {
            return type;
        }

        public void setType(GameActionType type) {
            this.type = type;
        }
    }
}
