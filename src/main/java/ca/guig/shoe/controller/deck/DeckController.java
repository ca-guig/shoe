package ca.guig.shoe.controller.deck;

import ca.guig.shoe.controller.Routes;
import ca.guig.shoe.domain.Deck;
import ca.guig.shoe.service.deck.DeckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class DeckController {

    private final DeckService service;

    public DeckController(DeckService service) {
        this.service = service;
    }

    @PostMapping(Routes.DECK_LIST)
    public ResponseEntity<Void> createDeck(@RequestBody Deck deck) {
        return ResponseEntity.created(buildLocation(service.createDeck(deck))).build();
    }

    @GetMapping(Routes.DECK)
    public Deck readDeck(@PathVariable String deckId) {
        return service.readDeck(deckId);
    }

    private static URI buildLocation(Deck deck) {
        return ServletUriComponentsBuilder.fromCurrentServletMapping().path(Routes.DECK).build(deck.getId());
    }
}
