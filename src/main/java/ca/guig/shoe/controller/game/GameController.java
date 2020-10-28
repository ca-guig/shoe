package ca.guig.shoe.controller.game;

import ca.guig.shoe.controller.Routes;
import ca.guig.shoe.domain.Game;
import ca.guig.shoe.service.game.GameNotFoundException;
import ca.guig.shoe.service.game.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping(Routes.GAME_LIST)
    public ResponseEntity<Void> create(@RequestBody Game game) {
        return ResponseEntity.created(buildLocation(service.createGame(game))).build();
    }

    @GetMapping(Routes.GAME_LIST)
    public List<Game> findAll() {
        return service.findAll();
    }

    @GetMapping(Routes.GAME)
    public Game read(@PathVariable String gameId) {
        return service.readGame(gameId);
    }

    @PutMapping(Routes.GAME)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String gameId, @RequestBody Game game) {
        service.updateGame(gameId, game);
    }

    @DeleteMapping(Routes.GAME)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String gameId) {
        service.deleteGame(gameId);
    }

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleGameNotFoundException() {}

    private static URI buildLocation(Game game) {
        return ServletUriComponentsBuilder.fromCurrentServletMapping().path("/rest/v1/games/{id}").build(game.getId());
    }
}
