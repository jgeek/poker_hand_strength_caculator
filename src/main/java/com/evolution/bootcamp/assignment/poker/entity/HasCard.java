package com.evolution.bootcamp.assignment.poker.entity;

import static java.util.stream.Collectors.*;

import com.evolution.bootcamp.assignment.poker.exception.BadFormatException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class HasCard {

    private static final List<Integer> FIVE_STRAIGHT = List.of(2, 3, 4, 5, 14);

    private String chars;
    private List<Card> cards;

    public HasCard() {

    }

    public HasCard(String chars) {
        Objects.requireNonNull(chars);
        long count = chars.chars().count();
        if (count % 2 != 0) {
            throw new RuntimeException("invalid number of characters");
        }
        this.chars = chars;
        this.cards = cards(chars);
    }

    /**
     * By splitting the card characters into two chars group, then we can create
     * {@link Card}s by this two chars groups.
     *
     * @param chars character of a hand.
     * @return list of cards object from given characters.
     */
    public List<Card> cards(String chars) {
        Objects.requireNonNull(chars, "hand is empty");
        if (chars.length() % 2 != 0) {
            throw new BadFormatException(this.getClass().getSimpleName(), chars);
        }
        final AtomicInteger counter = new AtomicInteger();
        List<Character> charArrBoxed = chars.chars()
                .mapToObj(c -> (char) c)
                .collect(toList());
        List<Card> cards = charArrBoxed.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 2)).values()
                .stream().map(twoChar -> new Card(twoChar.get(1), twoChar.get(0)))
                .collect(toList());

        return cards;
    }

    /**
     * @return integer value of each card. e.g 14 11 for J and 14 for A.
     */
    public List<Integer> numbers() {
        return cards.stream().mapToInt(Card::getIntVal).boxed().collect(toList());
    }

    /**
     * Sorts the cards of hand in ascending order base on theirs value.
     * If the hand is '2345A' that it is 5-straight, so replace the 'A' with '1'
     *
     * @return sorted list of cards
     */
    public List<Card> sortedCards() {
        List<Card> sorted = getCards().stream().sorted().collect(toList());
        var numbers = sorted.stream().mapToInt(Card::getIntVal).sorted().boxed().collect(toList());
        if (numbers.equals(FIVE_STRAIGHT)) {
            sorted.get(0).setValue('1');
            Collections.sort(sorted);
        }
        return sorted;
    }

    /**
     * @return A new hand with sorted cards
     */
    public Hand sortedOne() {
        List<Card> sorted = sortedCards();
        Hand hand = new Hand(getChars());
        hand.setCards(sorted);
        return hand;
    }

    /**
     * The hand is straight if no repeated card exists and also the difference of
     * minimum and maximum is 4 (because we just have 5 cards)
     *
     * @return the highest card value as straight hand strength if the hand is straight
     * otherwise -1.
     */
    public int straight() {
        List<Integer> numbers = numbers();
        Set<Integer> set = new HashSet<>(numbers);
        List<Card> cards = sortedCards();
        var max = cards.get(0).getIntVal();
        var min = cards.get(4).getIntVal();
        if (set.size() == 5 && max - min == 4) {
            return max;
        }
        return -1;
    }

    /**
     * The hand is flush if all suits are the same.
     *
     * @return the highest card value if the hand is flush
     * otherwise -1.
     */
    public int flush() {
        Set<Card.Suit> set = getCards().stream().map(Card::getSuit).collect(Collectors.toSet());
        if (set.size() == 1) {
            return getCards().stream().mapToInt(Card::getIntVal).max().getAsInt();
        }
        return -1;
    }

    /**
     * Check if the hand is straight and flush.
     *
     * @return the highest card value if the hand is straight and flush
     * otherwise -1.
     */
    public int isStraightFlash() {
        int straightValue = straight();
        if (straightValue > 0 && flush() > 0) {
            return straightValue;
        }
        return -1;
    }

    /**
     * @param n       kind number
     * @param reverse if passing true it first reverses the cards and then check
     *                for kind n. it's use case is just for two pair.
     *                Grouping the list by elements and then calculate the size of group
     *                value we can find how many time an element is repeated.
     * @return the value of kind if exist otherwise -1.
     * TODO: drop reverse param. a better design expected
     */
    public int kind(int n, boolean reverse) {
        Map<Integer, List<Integer>> map = getCards().stream().map(Card::getIntVal)
                .collect(Collectors.groupingBy(Function.identity()));
        Comparator<Map.Entry<Integer, List<Integer>>> comparator;
        if (reverse) {
            comparator = (o1, o2) -> o2.getKey() - o1.getKey();
        } else {
            comparator = Comparator.comparingInt(Map.Entry::getKey);
        }
        // Map<Integer, List<Integer>> reverseMap = new TreeMap<>(comparator);
        // reverseMap.putAll(map);
        return map.entrySet().stream().sorted(comparator).filter(e -> e.getValue().size() == n)
                .findAny().map(Map.Entry::getKey)
                .orElse(-1);
    }

    /**
     * The hand is two pair if we sort it ascending and descending, so two unique
     * pairs can be found.
     *
     * @return an array containing values of pair otherwise -1.
     */
    public int[] twoPair() {
        int firstPair = kind(2, false);
        int secondPair = -1;
        if (firstPair != -1) {
            // Hand reverseHand = new Hand(getChars());
            // reverseHand = reverseHand.sortedOne();
            // List<Card> reverseCards = reverseHand.sortedCards();
            // reverseHand.setCards(reverseCards);
            secondPair = kind(2, true);
            if (firstPair == secondPair) {
                secondPair = -1;
            }
        }
        return new int[] { firstPair, secondPair };
    }
}
