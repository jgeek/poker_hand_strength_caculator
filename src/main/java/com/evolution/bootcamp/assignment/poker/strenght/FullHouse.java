package com.evolution.bootcamp.assignment.poker.strenght;

import com.evolution.bootcamp.assignment.poker.entity.Hand;
import lombok.Getter;

@Getter
public class FullHouse extends HandStrength {
    private final int pairValue;

    public FullHouse(Hand hand, int base, int score, int pairValue) {
        super(hand, base, score);
        this.pairValue = pairValue;
    }

    /**
     * If tho hands are full house on the same value so comparing the value of
     * their pair.
     *
     * @param other other strength
     * @return -1/+1 for stronger/weaker. 0 of they are the same.
     */
    @Override
    public int compareTo(HandStrength other) {
        int compare = super.compareTo(other);
        if (compare == 0) {
            FullHouse fullHouse = (FullHouse) other;
            return fullHouse.getPairValue() - getPairValue();
        }
        return compare;
    }
}
