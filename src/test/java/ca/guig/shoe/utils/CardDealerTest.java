package ca.guig.shoe.utils;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import ca.guig.shoe.domain.Card;
import ca.guig.shoe.domain.DeckCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CardDealerTest {

    @Test
    void dealShouldDealGivenNumberOfCardFromDeckToHand() {
        List<DeckCard> deck = Arrays
                .stream(Card.values())
                .map((card) -> DeckCard.builder().withId("mock").withValue(card).build())
                .collect(toList());

        List<DeckCard> hand = new ArrayList<>();

        CardDealer dealer = new CardDealer(deck, hand, 5);
        dealer.deal();

        assertThat(deck).hasSize(47).element(0).hasFieldOrPropertyWithValue("value", Card.SIX_OF_HEARTS);
        assertThat(hand).hasSize(5).containsExactlyInAnyOrder(
                DeckCard.builder().withId("mock").withValue(Card.ACE_OF_HEARTS).build(),
                DeckCard.builder().withId("mock").withValue(Card.TWO_OF_HEARTS).build(),
                DeckCard.builder().withId("mock").withValue(Card.THREE_OF_HEARTS).build(),
                DeckCard.builder().withId("mock").withValue(Card.FOUR_OF_HEARTS).build(),
                DeckCard.builder().withId("mock").withValue(Card.FIVE_OF_HEARTS).build());
    }

    @Test
    void dealShouldDealAllAvailableCardsWhenNumberOfCardIsTooHigh() {
        List<DeckCard> deck = new ArrayList<>(Arrays.asList(
                DeckCard.builder().withId("mock").withValue(Card.ACE_OF_HEARTS).build(),
                DeckCard.builder().withId("mock").withValue(Card.TWO_OF_HEARTS).build(),
                DeckCard.builder().withId("mock").withValue(Card.THREE_OF_HEARTS).build()));

        List<DeckCard> hand = new ArrayList<>();

        CardDealer dealer = new CardDealer(deck, hand, 5);
        dealer.deal();

        assertThat(deck).hasSize(0);
        assertThat(hand).hasSize(3);
    }
}