package com.evolution.bootcamp.assignment.poker.strenght;

import static java.util.stream.Collectors.*;

import com.evolution.bootcamp.assignment.poker.entity.Card;
import com.evolution.bootcamp.assignment.poker.entity.Hand;
import java.util.List;
import java.util.stream.IntStream;

public class Pair extends HandStrength {
    public Pair(Hand hand, int base, int score) {
        super(hand, base, score);
    }

    /**
     * If tho hands are pair on the same value so comparing the next cards from 3th card.
     * It's because the cards are sorted descending here.
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
        List<Integer> numbers = IntStream.range(0, cards.size()).dropWhile(i -> i < 2).mapToObj(cards::get)
                .mapToInt(Card::getIntVal).boxed()
                .collect(toList());
        List<Integer> otherNumbers = IntStream.range(0, otherCards.size()).dropWhile(i -> i < 2)
                .mapToObj(otherCards::get)
                .mapToInt(Card::getIntVal).boxed()
                .collect(toList());
        for (int i = 0; i <= 2; i++) {
            if (numbers.get(i) != otherNumbers.get(i)) {
                return otherNumbers.get(i) - numbers.get(i);
            }
        }
        return 0;
    }
}
