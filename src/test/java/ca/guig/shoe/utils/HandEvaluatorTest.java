package ca.guig.shoe.utils;

import static org.assertj.core.api.Assertions.assertThat;

import ca.guig.shoe.domain.Card;
import ca.guig.shoe.domain.DeckCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class HandEvaluatorTest {

    @Test
    void calculateHandValue() {
        List<DeckCard> hand = new ArrayList<>(Arrays.asList(
                DeckCard.builder().withId("mock").withValue(Card.TEN_OF_DIAMONDS).build(),
                DeckCard.builder().withId("mock").withValue(Card.KING_OF_HEARTS).build(),
                DeckCard.builder().withId("mock").withValue(Card.THREE_OF_CLUBS).build()));

        int value = HandEvaluator.calculateValue(hand);

        assertThat(value).isEqualTo(26);
    }

}