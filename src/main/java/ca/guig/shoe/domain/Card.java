package ca.guig.shoe.domain;

import static ca.guig.shoe.domain.CardFace.ACE;
import static ca.guig.shoe.domain.CardFace.EIGHT;
import static ca.guig.shoe.domain.CardFace.FIVE;
import static ca.guig.shoe.domain.CardFace.FOUR;
import static ca.guig.shoe.domain.CardFace.JACK;
import static ca.guig.shoe.domain.CardFace.KING;
import static ca.guig.shoe.domain.CardFace.NINE;
import static ca.guig.shoe.domain.CardFace.QUEEN;
import static ca.guig.shoe.domain.CardFace.SEVEN;
import static ca.guig.shoe.domain.CardFace.SIX;
import static ca.guig.shoe.domain.CardFace.TEN;
import static ca.guig.shoe.domain.CardFace.THREE;
import static ca.guig.shoe.domain.CardFace.TWO;
import static ca.guig.shoe.domain.CardSuit.CLUBS;
import static ca.guig.shoe.domain.CardSuit.DIAMONDS;
import static ca.guig.shoe.domain.CardSuit.HEARTS;
import static ca.guig.shoe.domain.CardSuit.SPADES;

public enum Card {
    ACE_OF_HEARTS(HEARTS, ACE),
    TWO_OF_HEARTS(HEARTS, TWO),
    THREE_OF_HEARTS(HEARTS, THREE),
    FOUR_OF_HEARTS(HEARTS, FOUR),
    FIVE_OF_HEARTS(HEARTS, FIVE),
    SIX_OF_HEARTS(HEARTS, SIX),
    SEVEN_OF_HEARTS(HEARTS, SEVEN),
    EIGHT_OF_HEARTS(HEARTS, EIGHT),
    NINE_OF_HEARTS(HEARTS, NINE),
    TEN_OF_HEARTS(HEARTS, TEN),
    JACK_OF_HEARTS(HEARTS, JACK),
    QUEEN_OF_HEARTS(HEARTS, QUEEN),
    KING_OF_HEARTS(HEARTS, KING),

    ACE_OF_SPADES(SPADES, ACE),
    TWO_OF_SPADES(SPADES, TWO),
    THREE_OF_SPADES(SPADES, THREE),
    FOUR_OF_SPADES(SPADES, FOUR),
    FIVE_OF_SPADES(SPADES, FIVE),
    SIX_OF_SPADES(SPADES, SIX),
    SEVEN_OF_SPADES(SPADES, SEVEN),
    EIGHT_OF_SPADES(SPADES, EIGHT),
    NINE_OF_SPADES(SPADES, NINE),
    TEN_OF_SPADES(SPADES, TEN),
    JACK_OF_SPADES(SPADES, JACK),
    QUEEN_OF_SPADES(SPADES, QUEEN),
    KING_OF_SPADES(SPADES, KING),

    ACE_OF_CLUBS(CLUBS, ACE),
    TWO_OF_CLUBS(CLUBS, TWO),
    THREE_OF_CLUBS(CLUBS, THREE),
    FOUR_OF_CLUBS(CLUBS, FOUR),
    FIVE_OF_CLUBS(CLUBS, FIVE),
    SIX_OF_CLUBS(CLUBS, SIX),
    SEVEN_OF_CLUBS(CLUBS, SEVEN),
    EIGHT_OF_CLUBS(CLUBS, EIGHT),
    NINE_OF_CLUBS(CLUBS, NINE),
    TEN_OF_CLUBS(CLUBS, TEN),
    JACK_OF_CLUBS(CLUBS, JACK),
    QUEEN_OF_CLUBS(CLUBS, QUEEN),
    KING_OF_CLUBS(CLUBS, KING),

    ACE_OF_DIAMONDS(DIAMONDS, ACE),
    TWO_OF_DIAMONDS(DIAMONDS, TWO),
    THREE_OF_DIAMONDS(DIAMONDS, THREE),
    FOUR_OF_DIAMONDS(DIAMONDS, FOUR),
    FIVE_OF_DIAMONDS(DIAMONDS, FIVE),
    SIX_OF_DIAMONDS(DIAMONDS, SIX),
    SEVEN_OF_DIAMONDS(DIAMONDS, SEVEN),
    EIGHT_OF_DIAMONDS(DIAMONDS, EIGHT),
    NINE_OF_DIAMONDS(DIAMONDS, NINE),
    TEN_OF_DIAMONDS(DIAMONDS, TEN),
    JACK_OF_DIAMONDS(DIAMONDS, JACK),
    QUEEN_OF_DIAMONDS(DIAMONDS, QUEEN),
    KING_OF_DIAMONDS(DIAMONDS, KING);

    private final CardSuit suit;

    private final CardFace face;

    Card(CardSuit suit, CardFace face) {
        this.suit = suit;
        this.face = face;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public CardFace getFace() {
        return face;
    }
}
