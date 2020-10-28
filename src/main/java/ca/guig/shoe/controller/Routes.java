package ca.guig.shoe.controller;

public final class Routes {

    private static final String BASE_PATH = "/rest";

    public static final String DECK_LIST = BASE_PATH + "/v1/decks";
    public static final String DECK = DECK_LIST + "/{deckId}";

    public static final String GAME_LIST = BASE_PATH + "/v1/games";
    public static final String GAME = GAME_LIST + "/{gameId}";

    public static final String GAME_PLAYER_LIST = GAME + "/players";
    public static final String GAME_PLAYER = GAME_PLAYER_LIST + "/{playerId}";

    private Routes() {}
}
