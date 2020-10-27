package ca.guig.shoe.controller;

import ca.guig.shoe.domain.Game;
import ca.guig.shoe.service.GameNotFoundException;
import ca.guig.shoe.service.GameService;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/rest/v1/games")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Game game) {
        return ResponseEntity.created(buildLocation(service.createGame(game))).build();
    }

    @GetMapping
    public List<Game> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Game read(@PathVariable String id) {
        return service.readGame(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @RequestBody Game game) {
        service.updateGame(id, game);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.deleteGame(id);
    }

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleGameNotFoundException() {}

    private static URI buildLocation(Game game) {
        return ServletUriComponentsBuilder.fromCurrentServletMapping().path("/rest/v1/games/{id}").build(game.getId());
    }
}
