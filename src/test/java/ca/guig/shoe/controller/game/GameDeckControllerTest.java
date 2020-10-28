package ca.guig.shoe.controller.game;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.guig.shoe.controller.Routes;
import ca.guig.shoe.service.game.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = GameDeckController.class)
class GameDeckControllerTest {

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mvc;

    @Test
    void addDeckToShoe() throws Exception {
        String json = "{\"deckId\":\"12345\"}";
        mvc
                .perform(post(Routes.GAME_DECK_LIST, "1000").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());

        BDDMockito.verify(gameService).addDeckToShoe("1000", "12345");
    }
}