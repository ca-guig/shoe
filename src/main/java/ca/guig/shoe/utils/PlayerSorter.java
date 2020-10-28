package ca.guig.shoe.utils;

import ca.guig.shoe.domain.Player;

import java.util.Comparator;

public class PlayerSorter implements Comparator<Player> {

    @Override
    public int compare(Player left, Player right) {
        return Integer.compare(left.getHand().getValue(), right.getHand().getValue()) * -1;
    }
}
