package com.evolution.bootcamp.assignment.poker.strenght;

import com.evolution.bootcamp.assignment.poker.entity.Hand;
import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HandStrength implements Comparable<HandStrength> {

    public static final int STRAIGHT_FLUSH = 9;
    public static final int KIND_4 = 8;
    public static final int FULL_HOUSE = 7;
    public static final int FLUSH = 6;
    public static final int STRAIGHT = 5;
    public static final int KIND_3 = 4;
    public static final int TWO_PAIR = 3;
    public static final int PAIR = 2;
    public static final int HIGH_CARD = 1;

    private final Hand hand;
    // base is rank if the hand according to above constants.
    private final int base;
    // score of strength for hand. e.g if the hand is '7s5d7hAc' it's pair with the score of 7
    private final int score;

    /**
     * Stronger one becomes first.
     *
     * @param other other strength
     * @return -1/+1 for stronger/weaker. 0 of they are the same.
     */
    @Override
    public int compareTo(HandStrength other) {
        Comparator<HandStrength> comparing = Comparator.comparingInt(HandStrength::getBase)
                .thenComparingInt(HandStrength::getScore)
                .reversed();
        return comparing.compare(this, other);
    }
}
