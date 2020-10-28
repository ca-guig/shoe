package ca.guig.shoe.controller.game;

import ca.guig.shoe.controller.Routes;
import ca.guig.shoe.domain.Player;
import ca.guig.shoe.service.game.GameNotFoundException;
import ca.guig.shoe.service.game.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GamePlayerController {

    private final GameService service;

    public GamePlayerController(GameService service) {
        this.service = service;
    }

    @PostMapping(Routes.GAME_PLAYER_LIST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addPlayer(@PathVariable String gameId, @RequestBody Player player) {
        service.addPlayer(gameId, player);
    }

    @DeleteMapping(Routes.GAME_PLAYER)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePlayer(@PathVariable String gameId, @PathVariable String playerId) {
        service.removePlayer(gameId, playerId);
    }

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleGameNotFoundException() {}

}
