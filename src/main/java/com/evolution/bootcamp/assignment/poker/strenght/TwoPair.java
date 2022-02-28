package com.evolution.bootcamp.assignment.poker.strenght;

import com.evolution.bootcamp.assignment.poker.entity.Card;
import com.evolution.bootcamp.assignment.poker.entity.Hand;
import java.util.List;

public class TwoPair extends HandStrength {

    private final int highPair;
    private final int lowPair;

    public TwoPair(Hand hand, int highPair, int lowPair, int base, int score) {
        super(hand, base, score);
        this.highPair = highPair;
        this.lowPair = lowPair;
    }

    /**
     * If hands are two pairs on the same rank so comparing low pair values against each
     * after that comparing the 5th cards. It's because the cards are sorted descending here.
     *
     * @param other other strength
     * @return -1/+1 for stronger/weaker. 0 of they are the same.
     */
    @Override
    public int compareTo(HandStrength other) {
        int compare = super.compareTo(other);
        if (compare != 0) {
            return compare;
        }
        TwoPair otherTwoPair = (TwoPair) other;
        if (lowPair != otherTwoPair.highPair) {
            return otherTwoPair.lowPair - lowPair;
        }
        List<Card> cards = getHand().getCards();
        List<Card> otherCards = other.getHand().getCards();
        return otherCards.get(4).getIntVal() - cards.get(4).getIntVal();
    }
}
