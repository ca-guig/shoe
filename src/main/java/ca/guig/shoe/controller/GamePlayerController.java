package ca.guig.shoe.controller;

import ca.guig.shoe.domain.Player;
import ca.guig.shoe.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/rest/v1/games/{gameId}/players")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPlayer(@PathVariable String gameId, @RequestBody Player player) {
        service.addPlayer(gameId, player);
    }

    @DeleteMapping("/rest/v1/games/{gameId}/players/{playerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePlayer(@PathVariable String gameId, @PathVariable String playerId) {
        service.removePlayer(gameId, playerId);
    }
}
