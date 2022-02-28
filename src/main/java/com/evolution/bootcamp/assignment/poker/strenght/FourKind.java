package com.evolution.bootcamp.assignment.poker.strenght;

import com.evolution.bootcamp.assignment.poker.entity.Card;
import com.evolution.bootcamp.assignment.poker.entity.Hand;
import java.util.List;

public class FourKind extends HandStrength {
    public FourKind(Hand hand, int base, int score) {
        super(hand, base, score);
    }

    /**
     * If tho hands are four kind on the same value so comparing the 5th cards of
     * each hand. It's because the cards are sorted descending here.
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
        List<Card> cards = getHand().getCards();
        List<Card> otherCards = other.getHand().getCards();
        return otherCards.get(4).getIntVal() - cards.get(4).getIntVal();
    }
}
