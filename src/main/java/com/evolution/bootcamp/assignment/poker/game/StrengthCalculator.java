package com.evolution.bootcamp.assignment.poker.game;

import static com.evolution.bootcamp.assignment.poker.strenght.HandStrength.*;

import com.evolution.bootcamp.assignment.poker.entity.*;
import com.evolution.bootcamp.assignment.poker.strenght.*;

/**
 * In this class we check the strength of a hand from high priority one (straight flush) to lower one (high card).
 */
public class StrengthCalculator {

    private final Hand hand;

    public StrengthCalculator(Hand hand) {
        this.hand = hand.sortedOne();
    }

    public HandStrength calculate() {

        int straightFlash = hand.isStraightFlash();
        if (straightFlash > 0) {
            return new HandStrength(hand, STRAIGHT_FLUSH, straightFlash);
        }
        int fourKind = hand.kind(4, false);
        if (fourKind > 0) {
            return new FourKind(hand, 8, KIND_4);
        }
        int kind3 = hand.kind(3, false);
        int kind2 = hand.kind(2, false);
        if (kind2 > 0 && kind3 > 0) {
            return new FullHouse(hand, FULL_HOUSE, kind3, kind2);
        }
        int flush = hand.flush();
        if (flush > 0) {
            return new Flush(hand, FLUSH, flush);
        }
        int straight = hand.straight();
        if (straight > 0) {
            return new HandStrength(hand, STRAIGHT, straight);
        }
        if (kind3 > 0) {
            return new ThreeKind(hand, KIND_3, kind3);
        }
        int[] twoPair = hand.twoPair();
        if (twoPair[0] > 0 && twoPair[1] > 0) {
            return new TwoPair(hand, Math.max(twoPair[0], twoPair[1]), Math.min(twoPair[0], twoPair[1]), TWO_PAIR,
                    Math.max(twoPair[0], twoPair[1]));
        }
        if (kind2 > 0) {
            return new Pair(hand, PAIR, kind2);
        }
        int high = hand.getCards().stream().mapToInt(Card::getIntVal).max().getAsInt();
        return new HighCard(hand, HIGH_CARD, high);
    }
}
