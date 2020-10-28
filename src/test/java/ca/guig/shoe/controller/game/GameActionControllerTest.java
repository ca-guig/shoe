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

@WebMvcTest(controllers = GameActionController.class)
public class GameActionControllerTest {

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mvc;

    @Test
    void createActionShouldShuffleShoeWhenActionTypeIsShuffle() throws Exception {
        mvc
                .perform(post(Routes.GAME_ACTION_LIST, "1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"SHUFFLE_SHOE\"}"))
                .andExpect(status().isCreated());

        BDDMockito.verify(gameService).shuffleShoe("1000");
    }

    @Test
    void createActionShouldDealCardsWhenActionTypeIsDealCards() throws Exception {
        mvc
                .perform(post(Routes.GAME_ACTION_LIST, "1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"DEAL_CARDS\",\"playerId\":\"1234\",\"numberOfCards\":12}"))
                .andExpect(status().isCreated());

        BDDMockito.verify(gameService).dealCards("1000", "1234", 12);
    }
}
